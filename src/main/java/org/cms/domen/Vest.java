/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.domen;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jelena
 */
@Entity
@Table(name = "vest")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vest.findAll", query = "SELECT v FROM Vest v"),
    @NamedQuery(name = "Vest.findByVestID", query = "SELECT v FROM Vest v WHERE v.vestID = :vestID"),
    @NamedQuery(name = "Vest.findByDatum", query = "SELECT v FROM Vest v WHERE v.datum = :datum"),
    @NamedQuery(name = "Vest.findByNaslov", query = "SELECT v FROM Vest v WHERE v.naslov = :naslov"),
    @NamedQuery(name = "Vest.findByObjavljena", query = "SELECT v FROM Vest v WHERE v.objavljena = :objavljena")})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Vest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "vestID")
    private Integer vestID;
    @Column(name = "datum")
    @Temporal(TemporalType.DATE)
    private Date datum;
    @Size(max = 100)
    @Column(name = "naslov")
    private String naslov;
    @Lob
    @Size(max = 65535)
    @Column(name = "tekst")
    private String tekst;
    @Column(name = "objavljena")
    @JsonIgnore
    private Boolean objavljena;
    @OneToMany(mappedBy = "vest")
    @JsonIgnore
    private List<Fajl> fajlList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vest")
    @JsonIgnore
    private List<Komentar> komentarList;
    @JoinColumn(name = "kategorija", referencedColumnName = "kategorijaID")
    @ManyToOne
    @JsonIgnore
    private Kategorija kategorija;
    @JoinColumn(name = "korisnikID", referencedColumnName = "korisnikID")
    @ManyToOne
    @JsonIgnore
    private Korisnik korisnikID;

    public Vest() {
        fajlList = new ArrayList<>();
    }

    public Vest(Integer vestID) {
        this.vestID = vestID;
    }

    public Integer getVestID() {
        return vestID;
    }

    public void setVestID(Integer vestID) {
        this.vestID = vestID;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public Boolean getObjavljena() {
        return objavljena;
    }

    public void setObjavljena(Boolean objavljena) {
        this.objavljena = objavljena;
    }

    @XmlTransient
    public List<Fajl> getFajlList() {
        return fajlList;
    }

    public void setFajlList(List<Fajl> fajlList) {
        this.fajlList = fajlList;
    }

    @XmlTransient
    public List<Komentar> getKomentarList() {
        return komentarList;
    }

    public void setKomentarList(List<Komentar> komentarList) {
        this.komentarList = komentarList;
    }

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }

    public Korisnik getKorisnikID() {
        return korisnikID;
    }

    public void setKorisnikID(Korisnik korisnikID) {
        this.korisnikID = korisnikID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vestID != null ? vestID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vest)) {
            return false;
        }
        Vest other = (Vest) object;
        if ((this.vestID == null && other.vestID != null) || (this.vestID != null && !this.vestID.equals(other.vestID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.cms.domen.Vest[ vestID=" + vestID + " ]";
    }

}
