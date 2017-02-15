/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.controllers;

import org.cms.dao.KorisnikDao;
import org.cms.domen.Korisnik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class KorisnikController {

    @Autowired
    private KorisnikDao korisnikDao;

    @RequestMapping(value = "/registracija", method = RequestMethod.GET)
    public ModelAndView registracija() {
        ModelAndView mv = new ModelAndView("registracija");
        return mv;
    }

    @RequestMapping(value = "/registracija", method = RequestMethod.POST)
    public ModelAndView registracija(@ModelAttribute("korisnik") Korisnik korisnik) {
        ModelAndView mv = new ModelAndView("login");
        int id = korisnikDao.vratiID() + 1;
        korisnik.setKorisnikID(id);
        korisnik.setUloga("user");
        korisnikDao.sacuvajKorisnika(korisnik);
        return mv;

    }
}
