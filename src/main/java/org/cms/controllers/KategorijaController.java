/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.controllers;

import java.util.List;
import org.cms.dao.KategorijaDao;
import org.cms.domen.Kategorija;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/kategorije")
public class KategorijaController {

    @Autowired
    private KategorijaDao kategorijaDao;

    @RequestMapping(value = "/nova-kategorija", method = RequestMethod.GET)
    public ModelAndView dodajKategoriju() {
        ModelAndView mv = new ModelAndView("nova_kategorija");
        List<Kategorija> kategorije = kategorijaDao.vratiKategorije();
        if (kategorije == null) {
            System.out.println("Lista kategorija je prazna");
        }
        List<Kategorija> lk = kategorijaDao.vratiRootKategorije();

        mv.addObject("kategorije", kategorije);
        mv.addObject("rootKategorije", lk);
        mv.addObject("active", "nova-kategorija");

        return mv;
    }

    @RequestMapping(value = "/sve-kategorije", method = RequestMethod.GET)
    public ModelAndView sveKategorije() {
        ModelAndView mv = new ModelAndView("sve_kategorije");
        List<Kategorija> kategorije = kategorijaDao.vratiRootKategorije();
        mv.addObject("kategorije", kategorije);
        mv.addObject("active", "sve-kategorije");

        return mv;
    }

    @RequestMapping(value = "/sve-kategorije", method = RequestMethod.POST)
    public ModelAndView sveKategorije(@RequestParam(value = "prikazNaPocetnoj") int[] kat) {
        ModelAndView mv = new ModelAndView("sve_kategorije");
        List<Kategorija> kategorije = kategorijaDao.vratiRootKategorije();
        for (Kategorija k : kategorije) {
            k.setPrikazNaPocetnoj(false);
            kategorijaDao.izmeniKategoriju(k);
        }
        for (int i = 0; i < kat.length; i++) {
            Kategorija k = kategorijaDao.vratiKategoriju(kat[i]);
            k.setPrikazNaPocetnoj(true);
            kategorijaDao.izmeniKategoriju(k);
        }
        kategorije = kategorijaDao.vratiRootKategorije();
        mv.addObject("kategorije", kategorije);
        mv.addObject("active", "sve-kategorije");
        return mv;
    }

    @RequestMapping(value = "/nova-kategorija", method = RequestMethod.POST)
    public ModelAndView dodajKategoriju(@RequestParam(value = "naziv") String naziv, @RequestParam(value = "nadkategorije") int kategorija, @RequestParam(value = "checkbox", defaultValue = "off") String checkbox) {
        ModelAndView mv = new ModelAndView("nova_kategorija");

        Kategorija novaKategorija = new Kategorija();
        int novaKategorijaID = kategorijaDao.vratiID() + 1;
        novaKategorija.setKategorijaID(novaKategorijaID);
        novaKategorija.setNaziv(naziv);

        if (checkbox.equals("on")) {
            Kategorija k = kategorijaDao.vratiKategoriju(kategorija);
            novaKategorija.setPodkategorijaID(k);

        } else {
            novaKategorija.setPodkategorijaID(null);
        }
        kategorijaDao.sacuvajKategoriju(novaKategorija);
        List<Kategorija> kategorije = kategorijaDao.vratiKategorije();
        List<Kategorija> lk = kategorijaDao.vratiRootKategorije();
        mv.addObject("kategorije", kategorije);
        mv.addObject("rootKategorije", lk);
        mv.addObject("active", "nova-kategorija");

        return mv;

    }
}
