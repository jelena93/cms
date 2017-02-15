/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.dao;

import org.cms.domen.Korisnik;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class KorisnikDao {

    @Autowired
    private SessionFactory sessionFactory;

    public KorisnikDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Korisnik proveriKorisnika(String user) {
        Session session = this.sessionFactory.getCurrentSession();
        Query q = session.createQuery("from Korisnik where username=:u");
        q.setParameter("u", user);
        Korisnik korisnik = (Korisnik) q.uniqueResult();
        return korisnik;
    }

    @Transactional
    public void sacuvajKorisnika(Korisnik korisnik) {
        sessionFactory.getCurrentSession().save(korisnik);
    }

    @Transactional
    public int vratiID() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Korisnik.class).setProjection(Projections.max("korisnikID"));
        int id = 0;
        try {
            return (int) criteria.uniqueResult();
        } catch (Exception e) {
            return id;
        }

    }

}
