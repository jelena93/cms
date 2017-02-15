/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.domen;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jelena
 */
@Entity
@Table(name = "fajl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fajl.findAll", query = "SELECT f FROM Fajl f"),
    @NamedQuery(name = "Fajl.findByFajlID", query = "SELECT f FROM Fajl f WHERE f.fajlID = :fajlID"),
    @NamedQuery(name = "Fajl.findByNaziv", query = "SELECT f FROM Fajl f WHERE f.naziv = :naziv"),
    @NamedQuery(name = "Fajl.findByEkstenzija", query = "SELECT f FROM Fajl f WHERE f.ekstenzija = :ekstenzija"),
    @NamedQuery(name = "Fajl.findByPutanja", query = "SELECT f FROM Fajl f WHERE f.putanja = :putanja"),
    @NamedQuery(name = "Fajl.findByVelicina", query = "SELECT f FROM Fajl f WHERE f.velicina = :velicina")})
public class Fajl implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "fajlID")
    private Integer fajlID;
    @Size(max = 50)
    @Column(name = "naziv")
    private String naziv;
    @Size(max = 10)
    @Column(name = "ekstenzija")
    private String ekstenzija;
    @Size(max = 50)
    @Column(name = "putanja")
    private String putanja;
    @Column(name = "velicina")
    private Integer velicina;
    @JoinColumn(name = "vest", referencedColumnName = "vestID")
    @ManyToOne
    private Vest vest;

    public Fajl() {
    }

    public Fajl(Integer fajlID) {
        this.fajlID = fajlID;
    }

    public Integer getFajlID() {
        return fajlID;
    }

    public void setFajlID(Integer fajlID) {
        this.fajlID = fajlID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getEkstenzija() {
        return ekstenzija;
    }

    public void setEkstenzija(String ekstenzija) {
        this.ekstenzija = ekstenzija;
    }

    public String getPutanja() {
        return putanja;
    }

    public void setPutanja(String putanja) {
        this.putanja = putanja;
    }

    public Integer getVelicina() {
        return velicina;
    }

    public void setVelicina(Integer velicina) {
        this.velicina = velicina;
    }

    public Vest getVest() {
        return vest;
    }

    public void setVest(Vest vest) {
        this.vest = vest;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fajlID != null ? fajlID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fajl)) {
            return false;
        }
        Fajl other = (Fajl) object;
        if ((this.fajlID == null && other.fajlID != null) || (this.fajlID != null && !this.fajlID.equals(other.fajlID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.cms.domen.Fajl[ fajlID=" + fajlID + " ]";
    }
    
}
