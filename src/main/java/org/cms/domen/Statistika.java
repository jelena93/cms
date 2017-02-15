/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.domen;

/**
 *
 * @author Bojan
 */
public class Statistika {
    private String nazivKategorije;
    private long brojVestiPoKategoriji;

    public Statistika(String nazivKategorije, long brojVestiPoKategoriji) {
        this.nazivKategorije = nazivKategorije;
        this.brojVestiPoKategoriji = brojVestiPoKategoriji;
    }

    public String getNazivKategorije() {
        return nazivKategorije;
    }

    public void setNazivKategorije(String nazivKategorije) {
        this.nazivKategorije = nazivKategorije;
    }

    public long getBrojVestiPoKategoriji() {
        return brojVestiPoKategoriji;
    }

    public void setBrojVestiPoKategoriji(long brojVestiPoKategoriji) {
        this.brojVestiPoKategoriji = brojVestiPoKategoriji;
    }

    @Override
    public String toString() {
        return nazivKategorije;
    }
    
    
}
