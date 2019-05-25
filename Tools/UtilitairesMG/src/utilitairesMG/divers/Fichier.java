// ==========================================================================
// package utilitairesMG.divers
// --------------------------------------------------------------------------
// Classe Fichier : Gestion d'un fichier binaire a acces direct, avec des
//                  enregistrements de taille fixe
// --------------------------------------------------------------------------
// Cette classe derive de RandomAccessFile.
// Son constructeur ouvre le fichier "nom", precise le "mode" d'ouverture,
// et recoit la taille d'un enregistrement (tailleEnreg).
// ==========================================================================
package utilitairesMG.divers;

import java.io.*;
import java.nio.charset.Charset;

public class Fichier extends RandomAccessFile
{
    private int tailleEnreg;

// --------------------------------------------------------------------------
// Constructeurs
// --------------------------------------------------------------------------
    public Fichier(String nom, String mode, int tailleEnreg)
        throws IOException
    {
        super(nom, mode);
        this.tailleEnreg = tailleEnreg;
    }

    public Fichier(File fichier, String mode, int tailleEnreg)
        throws IOException
    {
        super(fichier, mode);
        this.tailleEnreg = tailleEnreg;
    }

// --------------------------------------------------------------------------
// Positionnement dans le fichier
// --------------------------------------------------------------------------
    public void seekFichier(int numeroEnregistrement) throws IOException
    {
        seek(tailleEnreg * numeroEnregistrement);
    }

// --------------------------------------------------------------------------
// Ecriture d'une String dans le fichier (traduite en bytes)
// nBytes : nombre de bytes ecrits
// --------------------------------------------------------------------------
    public void writeString(String s, int nBytes) throws IOException
    {
        int i;

        i = 0;
        while (i < s.length())               // Ecriture de la chaine
        {
            writeByte((int) s.charAt(i));
            i++;
        }
        while (i < nBytes)                   // Completer par des '\0'
        {
            writeByte(0);
            i++;
        }
    }

// --------------------------------------------------------------------------
// Lecture d'une String dans le fichier (stockee en bytes)
// nBytes : nombre de bytes a lire
// --------------------------------------------------------------------------
    public String readString(int nBytes) throws IOException
    {
        String s = "";
        int b;
        int i;

        i = 0;

        b = readUnsignedByte();
        i++;

        while (b != 0)
        {
            s += (char) b;
            b = readUnsignedByte();
            i++;
        }

        while (i < nBytes)
        {
            readByte();
            i++;
        }
        return s;
    }

// --------------------------------------------------------------------------
// Lecture d'un entier code en C : inversion des octets
// --------------------------------------------------------------------------
    public int readIntC() throws IOException
    {
        int retour;
        int i;

        i = readInt();
        retour = Integer.reverseBytes(i);

        return retour;
    }

// --------------------------------------------------------------------------
// Ecriture d'un entier code en Java vers un fichier code en C :
// inversion des octets
// --------------------------------------------------------------------------
    public void writeIntC(int v) throws IOException
    {
        int i;
        i = Integer.reverseBytes(v);
        writeInt(i);
    }

// --------------------------------------------------------------------------
// Lecture d'un long code en C : inversion des octets
// --------------------------------------------------------------------------
    public long readLongC() throws IOException
    {
        long retour;
        long i;

        i = readLong();
        retour = Long.reverseBytes(i);

        return retour;
    }

// --------------------------------------------------------------------------
// Ecriture d'un long code en Java vers un fichier code en C :
// inversion des octets
// --------------------------------------------------------------------------
    public void writeLongC(long v) throws IOException
    {
        long i;
        i = Long.reverseBytes(v);
        writeLong(i);
    }

// --------------------------------------------------------------------------
// Lecture d'un flottant code en C : inversion des octets, puis appel de
// Float.intBitsToFloat qui retourne un réel à partir d'un entier de type
// binaire
// --------------------------------------------------------------------------
    public float readFloatC() throws IOException
    {
        int i;
        i = readIntC();

        return Float.intBitsToFloat(i);
    }

// --------------------------------------------------------------------------
// Ecriture d'un reel code en Java vers un fichier code en C :
// Appel de Float.floatToIntBits qui effectue une conversion en un entier
// de type binaire, puis inversion des octets.
// --------------------------------------------------------------------------
    public void writeFloatC(float v) throws IOException
    {
        int entier;
        entier = Float.floatToIntBits(v);

        writeIntC(entier);
    }

// --------------------------------------------------------------------------
// Lecture d'un double code en C : inversion des octets, puis appel de
// Double.longBitsToDouble qui retourne un double à partir d'un long de
// type binaire
// --------------------------------------------------------------------------
    public double readDoubleC() throws IOException
    {
        long i;
        i = this.readLongC();

        return Double.longBitsToDouble(i);
    }

// --------------------------------------------------------------------------
// Ecriture d'un double code en Java vers un fichier code en C :
// Appel de Double.doubleToLongBits qui effectue une conversion en un long
// de type binaire, puis inversion des octets.
// --------------------------------------------------------------------------
    public void writeDoubleC(double v) throws IOException
    {
        long entier;
        entier = Double.doubleToLongBits(v);

        writeLongC(entier);
    }
}
