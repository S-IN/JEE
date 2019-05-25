/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gnmi
 */
@Entity
@Table(name = "VERSEMENT")
@XmlRootElement
@NamedQueries(
    {
        @NamedQuery(name = "Versement.findAll", query = "SELECT v FROM Versement v"),
        @NamedQuery(name = "Versement.findByNumero", query = "SELECT v FROM Versement v WHERE v.numero = :numero"),
        @NamedQuery(name = "Versement.findByDate", query = "SELECT v FROM Versement v WHERE v.date = :date"),
        @NamedQuery(name = "Versement.findByMontant", query = "SELECT v FROM Versement v WHERE v.montant = :montant")
    })
public class Versement implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUMERO")
    private Integer numero;
    @Column(name = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MONTANT")
    private BigDecimal montant;
    @JoinColumn(name = "NUMERO_CONTACT", referencedColumnName = "NUMERO")
    @ManyToOne(optional = false)
    private Contact numeroContact;

    public Versement()
    {
    }

    public Versement(Integer numero)
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

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public BigDecimal getMontant()
    {
        return montant;
    }

    public void setMontant(BigDecimal montant)
    {
        this.montant = montant;
    }

    public Contact getNumeroContact()
    {
        return numeroContact;
    }

    public void setNumeroContact(Contact numeroContact)
    {
        this.numeroContact = numeroContact;
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
        if (!(object instanceof Versement))
        {
            return false;
        }
        Versement other = (Versement) object;
        if ((this.numero == null && other.numero != null) || (this.numero != null && !this.numero.equals(other.numero)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "tables.Versement[ numero=" + numero + " ]";
    }

}
