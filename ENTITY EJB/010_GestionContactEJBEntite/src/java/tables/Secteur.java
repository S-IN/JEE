/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "SECTEUR")
@XmlRootElement
@NamedQueries(
    {
        @NamedQuery(name = "Secteur.findAll", query = "SELECT s FROM Secteur s"),
        @NamedQuery(name = "Secteur.findByCode", query = "SELECT s FROM Secteur s WHERE s.code = :code"),
        @NamedQuery(name = "Secteur.findByLibelle", query = "SELECT s FROM Secteur s WHERE s.libelle = :libelle")
    })
public class Secteur implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODE")
    private Integer code;
    @Size(max = 30)
    @Column(name = "LIBELLE")
    private String libelle;
    @OneToMany(mappedBy = "codeSecteur")
    private Collection<Contact> contactCollection;

    public Secteur()
    {
    }

    public Secteur(Integer code)
    {
        this.code = code;
    }

    public Integer getCode()
    {
        return code;
    }

    public void setCode(Integer code)
    {
        this.code = code;
    }

    public String getLibelle()
    {
        return libelle;
    }

    public void setLibelle(String libelle)
    {
        this.libelle = libelle;
    }

    @XmlTransient
    public Collection<Contact> getContactCollection()
    {
        return contactCollection;
    }

    public void setContactCollection(Collection<Contact> contactCollection)
    {
        this.contactCollection = contactCollection;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Secteur))
        {
            return false;
        }
        Secteur other = (Secteur) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "tables.Secteur[ code=" + code + " ]";
    }

}
