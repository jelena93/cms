/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.domen;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jelena
 */
@Entity
@Table(name = "komentar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Komentar.findAll", query = "SELECT k FROM Komentar k"),
    @NamedQuery(name = "Komentar.findByVestId", query = "SELECT k FROM Komentar k WHERE k.komentarPK.vestId = :vestId"),
    @NamedQuery(name = "Komentar.findByKorisnikId", query = "SELECT k FROM Komentar k WHERE k.komentarPK.korisnikId = :korisnikId"),
    @NamedQuery(name = "Komentar.findByDatum", query = "SELECT k FROM Komentar k WHERE k.komentarPK.datum = :datum")})
public class Komentar implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KomentarPK komentarPK;
    @Lob
    @Size(max = 65535)
    @Column(name = "sadrzaj")
    private String sadrzaj;
    @JoinColumn(name = "korisnik_id", referencedColumnName = "korisnikID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Korisnik korisnik;
    @JoinColumn(name = "vest_id", referencedColumnName = "vestID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Vest vest;

    public Komentar() {
    }

    public Komentar(KomentarPK komentarPK) {
        this.komentarPK = komentarPK;
    }

    public Komentar(int vestId, int korisnikId, Date datum) {
        this.komentarPK = new KomentarPK(vestId, korisnikId, datum);
    }

    public KomentarPK getKomentarPK() {
        return komentarPK;
    }

    public void setKomentarPK(KomentarPK komentarPK) {
        this.komentarPK = komentarPK;
    }

    public String getSadrzaj() {
        return sadrzaj;
    }

    public void setSadrzaj(String sadrzaj) {
        this.sadrzaj = sadrzaj;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
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
        hash += (komentarPK != null ? komentarPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Komentar)) {
            return false;
        }
        Komentar other = (Komentar) object;
        if ((this.komentarPK == null && other.komentarPK != null) || (this.komentarPK != null && !this.komentarPK.equals(other.komentarPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.cms.domen.Komentar[ komentarPK=" + komentarPK + " ]";
    }
    
}
