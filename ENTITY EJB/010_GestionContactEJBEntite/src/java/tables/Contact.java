/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gnmi
 */
@Entity
@Table(name = "CONTACT")
@XmlRootElement
@NamedQueries(
    {
        @NamedQuery(name = "Contact.findAll", query = "SELECT c FROM Contact c"),
        @NamedQuery(name = "Contact.findByNumero", query = "SELECT c FROM Contact c WHERE c.numero = :numero"),
        @NamedQuery(name = "Contact.findByNom", query = "SELECT c FROM Contact c WHERE c.nom = :nom"),
        @NamedQuery(name = "Contact.findByAdresse", query = "SELECT c FROM Contact c WHERE c.adresse = :adresse"),
        @NamedQuery(name = "Contact.findByCodePostal", query = "SELECT c FROM Contact c WHERE c.codePostal = :codePostal"),
        @NamedQuery(name = "Contact.findByVille", query = "SELECT c FROM Contact c WHERE c.ville = :ville")
    })
public class Contact implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUMERO")
    private Integer numero;
    @Size(max = 20)
    @Column(name = "NOM")
    private String nom;
    @Size(max = 50)
    @Column(name = "ADRESSE")
    private String adresse;
    @Size(max = 5)
    @Column(name = "CODE_POSTAL")
    private String codePostal;
    @Size(max = 20)
    @Column(name = "VILLE")
    private String ville;
    @JoinColumn(name = "CODE_SECTEUR", referencedColumnName = "CODE")
    @ManyToOne
    private Secteur codeSecteur;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numeroContact")
    private Collection<Versement> versementCollection;

    public Contact()
    {
    }

    public Contact(Integer numero)
    {
        this.numero = numero;
    }

    public Integer getNumero()
    {
        return numero;
    }

    public void setNumero(Integer numero)
    {
        this.numero = numero;
    }

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public String getAdresse()
    {
        return adresse;
    }

    public void setAdresse(String adresse)
    {
        this.adresse = adresse;
    }

    public String getCodePostal()
    {
        return codePostal;
    }

    public void setCodePostal(String codePostal)
    {
        this.codePostal = codePostal;
    }

    public String getVille()
    {
        return ville;
    }

    public void setVille(String ville)
    {
        this.ville = ville;
    }

    public Secteur getCodeSecteur()
    {
        return codeSecteur;
    }

    public void setCodeSecteur(Secteur codeSecteur)
    {
        this.codeSecteur = codeSecteur;
    }

    @XmlTransient
    public Collection<Versement> getVersementCollection()
    {
        return versementCollection;
    }

    public void setVersementCollection(Collection<Versement> versementCollection)
    {
        this.versementCollection = versementCollection;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (numero != null ? numero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contact))
        {
            return false;
        }
        Contact other = (Contact) object;
        if ((this.numero == null && other.numero != null) || (this.numero != null && !this.numero.equals(other.numero)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "tables.Contact[ numero=" + numero + " ]";
    }

}
