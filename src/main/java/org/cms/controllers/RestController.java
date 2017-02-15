/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.controllers;

import java.util.List;
import org.cms.dao.KategorijaDao;
import org.cms.dao.VestDao;
import org.cms.domen.Kategorija;
import org.cms.domen.Vest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Vukasin
 */
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private VestDao vestDao;
    @Autowired
    private KategorijaDao kategorijaDao;

    @RequestMapping(value = "/api/kategorije/", method = RequestMethod.GET)
    public ResponseEntity<List<Kategorija>> vratiSveKategorije() {
        List<Kategorija> kategorije = kategorijaDao.vratiRootKategorije();
        if (kategorije.isEmpty()) {
            return new ResponseEntity<List<Kategorija>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Kategorija>>(kategorije, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/vesti/", method = RequestMethod.GET)
    public ResponseEntity<List<Vest>> vratiSveVesti() {
        List<Vest> vesti = vestDao.vratiSveVesti();
        if (vesti.isEmpty()) {
            return new ResponseEntity<List<Vest>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Vest>>(vesti, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/vesti/{id}/json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vest> vratiVestJson(@PathVariable("id") int id) {
        Vest v = vestDao.prikaziVest(id);
        if (v == null) {
            System.out.println("vest je null");
            return new ResponseEntity<Vest>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Vest>(v, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/vesti/{id}/xml", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Vest> vratiVestXml(@PathVariable("id") int id) {
        Vest v = vestDao.prikaziVest(id);
        if (v == null) {
            System.out.println("vest je null");
            return new ResponseEntity<Vest>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Vest>(v, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/vesti/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Vest> izmeniVest(@PathVariable("id") int id, @RequestBody Vest vest) {
        Vest v = vestDao.prikaziVest(id);  
        if (v==null) {
            return new ResponseEntity<Vest>(HttpStatus.NOT_FOUND);
        }
        v.setNaslov(vest.getNaslov());
        v.setTekst(vest.getTekst());
        v.setObjavljena(vest.getObjavljena()); 
        vestDao.izmeniVest(v);
        return new ResponseEntity<Vest>(v, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/vesti/", method = RequestMethod.POST)
    public ResponseEntity<Void> kreirajVest(@RequestBody Vest vest,UriComponentsBuilder ucBuilder) {
        vestDao.sacuvajVest(vest);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/vesti/{id}").buildAndExpand(vest.getVestID()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

}
