/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.domen;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
 * @author Jelena
 */
@Entity
@Table(name = "kategorija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kategorija.findAll", query = "SELECT k FROM Kategorija k"),
    @NamedQuery(name = "Kategorija.findByKategorijaID", query = "SELECT k FROM Kategorija k WHERE k.kategorijaID = :kategorijaID"),
    @NamedQuery(name = "Kategorija.findByNaziv", query = "SELECT k FROM Kategorija k WHERE k.naziv = :naziv"),
    @NamedQuery(name = "Kategorija.findByPrikazNaPocetnoj", query = "SELECT k FROM Kategorija k WHERE k.prikazNaPocetnoj = :prikazNaPocetnoj")})
public class Kategorija implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "kategorijaID")
    private Integer kategorijaID;
    @Size(max = 50)
    @Column(name = "naziv")
    private String naziv;
    @Column(name = "prikazNaPocetnoj")
    private Boolean prikazNaPocetnoj;
    @JsonIgnore
    @OneToMany(mappedBy = "kategorija")
    private List<Vest> vestList;
    @JsonIgnore
    @OneToMany(mappedBy = "podkategorijaID")
    private List<Kategorija> kategorijaList;
    @JoinColumn(name = "podkategorijaID", referencedColumnName = "kategorijaID")
    @ManyToOne
    @JsonIgnore
    private Kategorija podkategorijaID;

    public Kategorija() {
    }

    public Kategorija(Integer kategorijaID) {
        this.kategorijaID = kategorijaID;
    }

    public Integer getKategorijaID() {
        return kategorijaID;
    }

    public void setKategorijaID(Integer kategorijaID) {
        this.kategorijaID = kategorijaID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Boolean getPrikazNaPocetnoj() {
        return prikazNaPocetnoj;
    }

    public void setPrikazNaPocetnoj(Boolean prikazNaPocetnoj) {
        this.prikazNaPocetnoj = prikazNaPocetnoj;
    }

    @XmlTransient
    public List<Vest> getVestList() {
        return vestList;
    }

    public void setVestList(List<Vest> vestList) {
        this.vestList = vestList;
    }

    @XmlTransient
    public List<Kategorija> getKategorijaList() {
        return kategorijaList;
    }

    public void setKategorijaList(List<Kategorija> kategorijaList) {
        this.kategorijaList = kategorijaList;
    }

    public Kategorija getPodkategorijaID() {
        return podkategorijaID;
    }

    public void setPodkategorijaID(Kategorija podkategorijaID) {
        this.podkategorijaID = podkategorijaID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kategorijaID != null ? kategorijaID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kategorija)) {
            return false;
        }
        Kategorija other = (Kategorija) object;
        if ((this.kategorijaID == null && other.kategorijaID != null) || (this.kategorijaID != null && !this.kategorijaID.equals(other.kategorijaID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.cms.domen.Kategorija[ kategorijaID=" + kategorijaID + " ]";
    }
    
}
