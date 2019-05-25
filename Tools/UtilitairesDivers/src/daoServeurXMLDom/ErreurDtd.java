package daoServeurXMLDom;


import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

// ==========================================================================
// Classe de gestion des erreurs liees a la verification DTD
// ==========================================================================
public class ErreurDtd implements ErrorHandler
{
    public void warning(SAXParseException exception) throws SAXException
    {
        System.out.println("Warning : " + exception.getMessage());
    }

    public void error(SAXParseException exception) throws SAXException
    {
        System.out.println("Erreur : " + exception.getMessage());
    }

    public void fatalError(SAXParseException exception) throws SAXException
    {
        System.out.println("Erreur Fatale : " + exception.getMessage());
    }
}