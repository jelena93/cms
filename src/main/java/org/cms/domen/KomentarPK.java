/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.domen;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Jelena
 */
@Embeddable
public class KomentarPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "vest_id")
    private int vestId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "korisnik_id")
    private int korisnikId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datum;

    public KomentarPK() {
    }

    public KomentarPK(int vestId, int korisnikId, Date datum) {
        this.vestId = vestId;
        this.korisnikId = korisnikId;
        this.datum = datum;
    }

    public int getVestId() {
        return vestId;
    }

    public void setVestId(int vestId) {
        this.vestId = vestId;
    }

    public int getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        this.korisnikId = korisnikId;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) vestId;
        hash += (int) korisnikId;
        hash += (datum != null ? datum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KomentarPK)) {
            return false;
        }
        KomentarPK other = (KomentarPK) object;
        if (this.vestId != other.vestId) {
            return false;
        }
        if (this.korisnikId != other.korisnikId) {
            return false;
        }
        if ((this.datum == null && other.datum != null) || (this.datum != null && !this.datum.equals(other.datum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.cms.domen.KomentarPK[ vestId=" + vestId + ", korisnikId=" + korisnikId + ", datum=" + datum + " ]";
    }
    
}
