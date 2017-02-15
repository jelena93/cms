/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.cms.dao.FajlDao;
import org.cms.dao.KategorijaDao;
import org.cms.dao.VestDao;
import org.cms.domen.Fajl;
import org.cms.domen.Kategorija;
import org.cms.domen.Korisnik;
import org.cms.domen.Vest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/vesti")
public class VestController {

    @Autowired
    private FajlDao fajlDao;

    @Autowired
    private VestDao vestDao;

    @Autowired
    private KategorijaDao kategorijaDao;

    @RequestMapping(value = "/nova-vest", method = RequestMethod.GET)
    public ModelAndView novaVest() {
        ModelAndView mv = new ModelAndView("nova_vest");
        List<Kategorija> sveKategorije = kategorijaDao.vratiRootKategorije();
        mv.addObject("kategorije", sveKategorije);
        return mv;
    }

    @RequestMapping(value = "/nova-vest", method = RequestMethod.POST)
    public ModelAndView novaVestDodaj(@RequestParam(value = "naslov") String naslov,
            @RequestParam(value = "tekst") String tekst, @RequestParam(value = "kategorije") int kategorija,
            @RequestParam(value = "file") MultipartFile[] files,
            @RequestParam(value = "korisnikID") int korisnikID, @RequestParam(value = "objavljena", required = false) boolean objavljena) throws ParseException {

        Vest v = new Vest();
        v.setVestID(vestDao.vratiID() + 1);
        v.setDatum(new Date());
        v.setNaslov(naslov);
        v.setTekst(tekst);
        v.setObjavljena(objavljena);
        Korisnik k = new Korisnik(korisnikID);
        v.setKorisnikID(k);
        Kategorija kat = kategorijaDao.vratiKategoriju(kategorija);
        v.setKategorija(kat);
        vestDao.sacuvajVest(v);

        if (!daLiSuPrazni(files)) {
            for (int i = 0; i < files.length; i++) {
                try {
                    byte[] bytes = files[i].getBytes();
                    String putanja = "C:" + File.separator + "wamp" + File.separator + "www" + File.separator + "images";
                    File dir = new File(putanja);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    String nazivFajla = files[i].getOriginalFilename();
                    File serverFile = new File(dir.getAbsolutePath()
                            + File.separator + nazivFajla);
                    try (BufferedOutputStream stream = new BufferedOutputStream(
                            new FileOutputStream(serverFile))) {
                        stream.write(bytes);
                    }

                    Fajl fajl = new Fajl();
                    int idFajla = fajlDao.vratiID() + 1;
                    System.out.println("ID fajla je: " + idFajla);
                    System.out.println(files[i].getOriginalFilename());
                    String ekstenzija = files[i].getOriginalFilename().split("\\.")[1];
                    if (!ekstenzija.equalsIgnoreCase("jpg")) {
                        throw new Exception("Morate uneti fajl u jpg formatu!");
                    }
                    System.out.println(ekstenzija);
                    fajl.setFajlID(idFajla);
                    fajl.setNaziv(nazivFajla.split("\\.")[0]);
                    fajl.setEkstenzija(ekstenzija);
                    fajl.setVelicina((int) files[i].getSize());
                    fajl.setPutanja(putanja);
                    fajl.setVest(v);
                    fajlDao.sacuvajFajl(fajl);
                    if (v.getFajlList() != null) {
                        v.getFajlList().add(fajl);
                    } else {
                        System.out.println("Lista nije inicijalizovana");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return new ModelAndView("greska", "greska", "Greska pri upload-u fajla " + " => " + e.getMessage());

                }
            }
        }

        ModelAndView mv = new ModelAndView("redirect:nova-vest");
        List<Kategorija> sveKategorije = kategorijaDao.vratiRootKategorije();
        mv.addObject("kategorije", sveKategorije);
        return mv;
    }

    private boolean daLiSuPrazni(MultipartFile[] files) {
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                System.out.println("Fajl prazan");
                return true;
            } else {
                System.out.println("naziv:" + file.getName());
            }
        }
        return false;
    }

    @RequestMapping(value = "/sve-vesti", method = RequestMethod.GET)
    public ModelAndView sveVesti() {
        ModelAndView mv = new ModelAndView("sve_vesti");
        mv.addObject("vesti", vestDao.vratiSveVesti());
        return mv;
    }

    @RequestMapping(value = "/sve-vesti", method = RequestMethod.POST)
    public ModelAndView sveVesti(@RequestParam(value = "objavljena") int[] kat) {
        ModelAndView mv = new ModelAndView("sve_vesti");
        List<Vest> vesti = vestDao.vratiSveVesti();
        for (Vest v : vesti) {
            v.setObjavljena(false);
            vestDao.izmeniVest(v);
        }
        for (int i = 0; i < kat.length; i++) {
            Vest v = vestDao.prikaziVest(kat[i]);
            v.setObjavljena(true);
            vestDao.izmeniVest(v);
        }
        vesti = vestDao.vratiSveVesti();
        mv.addObject("vesti", vesti);
        return mv;
    }

}
