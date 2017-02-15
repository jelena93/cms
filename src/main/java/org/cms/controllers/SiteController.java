/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.controllers;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.cms.dao.KategorijaDao;
import org.cms.dao.KomentarDao;
import org.cms.dao.VestDao;
import org.cms.domen.Kategorija;
import org.cms.domen.Komentar;
import org.cms.domen.KomentarPK;
import org.cms.domen.Vest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SiteController {

    @Autowired
    private VestDao vestDao;
    @Autowired
    private KategorijaDao kategorijaDao;
    @Autowired
    private KomentarDao komentarDao;

     @RequestMapping("/pocetna")
    public ModelAndView home(HttpSession session, @RequestParam("sort") String sort) {
        System.out.println(sort);
        ModelAndView mv = new ModelAndView("pocetna");
        List<Kategorija> lk = kategorijaDao.vratiRootKategorije();
        List<Vest> vesti = null;
        if (sort.equals("desc")) {
            vesti = vestDao.vratiObjavljeneVesti();
        }
        else{
            vesti = vestDao.vratiObjavljeneVestiInverzanSort();
        }
        mv.addObject("vesti", vesti);
        mv.addObject("kategorije", lk);
        mv.addObject("sub_kat", kategorijaDao.vratiKategorije());
        System.out.println("sesijaaa: " + session.getAttribute("user"));
        return mv;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpSession session) {
        ModelAndView mv;
        if (session.getAttribute("user") != null) {
            mv = new ModelAndView("redirect:/pocetna");
        } else {
            mv = new ModelAndView("login");
        }
        return mv;
    }

    @RequestMapping(value = "/vesti/prikaz_vesti")
    public ModelAndView vestDetalji(@RequestParam("vestID") int vestID) {
        ModelAndView mv;
        Vest vest = vestDao.prikaziVest(vestID);
        if (!vest.getObjavljena()) {
            mv = new ModelAndView("redirect:/pocetna");
        } else {
            mv = new ModelAndView("prikaz_vesti");
            List<Kategorija> lk = kategorijaDao.vratiRootKategorije();
            mv.addObject("kategorije", lk);
            mv.addObject("sub_kat", kategorijaDao.vratiKategorije());
            mv.addObject("vest", vest);
            mv.addObject("komentari", komentarDao.vratiKomentareVesti(vestID));
            mv.addObject("link", vestID);
        }

        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/vesti/postavi-komentar", method = RequestMethod.POST)
    public int postaviKomentar(@RequestParam("sadrzaj") String sadrzaj, @RequestParam("vestID") String vestID, @RequestParam("korisnikID") String korisnikID) {
        System.out.println("sadrzaj:" + sadrzaj + ",vestID:" + vestID + ",korisnikID:" + korisnikID);
        KomentarPK komentarPK = new KomentarPK(Integer.parseInt(vestID), Integer.parseInt(korisnikID), new Date());
        Komentar komentar = new Komentar(komentarPK);
        komentar.setSadrzaj(sadrzaj);
        komentarDao.sacuvajKomentar(komentar);
//        
//        ModelAndView mv = new ModelAndView("redirect:prikaz_vesti?vestID=" + vestID);
//        KomentarPK komentarPK = new KomentarPK(vestID, korisnikID, new Date());
//        Komentar komentar = new Komentar(komentarPK);
//        komentar.setSadrzaj(sadrzaj);
//        komentarDao.sacuvajKomentar(komentar);
//        return mv;
        return komentarDao.vratiKomentareVesti(Integer.parseInt(vestID)).size();
    }

    @RequestMapping(value = "/vesti_kategorija")
    public ModelAndView prikazVestiKategorije(@RequestParam("kategorijaID") int kategorijaID) {
        ModelAndView mv = new ModelAndView("prikaz_vesti_kategorije");
        mv.addObject("kat", kategorijaDao.vratiKategoriju(kategorijaID));
        List<Kategorija> lk = kategorijaDao.vratiRootKategorije();
        List<Vest> vestiKategorije = vestDao.vratiObjavljeneVesti(kategorijaID);
        List<Vest> vesti = vestDao.vratiObjavljeneVesti();
        mv.addObject("vesti", vesti);
        mv.addObject("vestiKategorije", vestiKategorije);
        mv.addObject("kategorije", lk);
        mv.addObject("sub_kat", kategorijaDao.vratiKategorije());

        return mv;
    }

}
