/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.cms.domen.Kategorija;
import org.cms.domen.Statistika;
import org.cms.domen.Vest;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class VestDao {

    @Autowired
    private SessionFactory sessionFactory;

    public VestDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Vest> vratiSveVesti() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Vest> vesti = session.createQuery("from Vest order by datum desc, vestID desc").list();
        return vesti;
    }

    @Transactional
    public List<Vest> vratiObjavljeneVesti() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Vest> vesti = session.createQuery("from Vest where objavljena=1 order by datum desc, vestID desc").list();
        return vesti;
    }

    @Transactional
    public List<Vest> vratiObjavljeneVesti(int kategorijaID) {
        Session session = this.sessionFactory.getCurrentSession();
        List<Vest> vesti = session.createQuery("from Vest where objavljena=1 and kategorija=" + kategorijaID + " order by datum desc, vestID desc").list();
        return vesti;
    }

    @Transactional
    public Statistika[] vratiParametreZaStatistiku() {
        Session session = this.sessionFactory.getCurrentSession();
        String upit = "SELECT kategorija.naziv as naziv,COUNT(vest.kategorija) AS brojClanaka FROM vest "
                + "LEFT JOIN kategorija ON vest.kategorija=kategorija.kategorijaID GROUP BY kategorija";
        List<Object[]> redovi = session.createSQLQuery(upit).addScalar("naziv", StringType.INSTANCE).addScalar("brojClanaka", LongType.INSTANCE).list();
        Statistika[] statistike = new Statistika[redovi.size()];
        for (int i = 0; i < redovi.size(); i++) {
            Statistika s = new Statistika((String) redovi.get(i)[0], (Long) redovi.get(i)[1]);
            statistike[i] = s;
        }
//        for (Statistika s : lista) {
//            System.out.println(s.getNazivKategorije() +" :"+s.getBrojVestiPoKategoriji());
//        }
        return statistike;
    }

    @Transactional
    public List<Vest> vratiSveVestiRootKategorije(Kategorija kat) {
        Session session = this.sessionFactory.getCurrentSession();
        List<Vest> vesti = session.
                createQuery("from Vest v join Kategorija k on v.kategorija=k.kategorijaID where (v.kategorija=(select kategorijaID from Kategorija where )) order by datum desc, vestID desc").list();
        return vesti;
    }

    @Transactional
    public Vest prikaziVest(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Vest vest = (Vest) session.load(Vest.class, id);
        return vest;
    }

    @Transactional
    public void sacuvajVest(Vest vest) {
        sessionFactory.getCurrentSession().save(vest);
    }

    @Transactional
    public void izmeniVest(Vest vest) {
        sessionFactory.getCurrentSession().update(vest);
    }

    @Transactional
    public int vratiID() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Vest.class).setProjection(Projections.max("vestID"));
        int id = 0;
        try {
            return (int) criteria.uniqueResult();
        } catch (Exception e) {
            return id;
        }
    }
    @Transactional
    public List<Vest> vratiObjavljeneVestiInverzanSort() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Vest> vesti = session.createQuery("from Vest where objavljena=1 order by datum asc, vestID desc").list();
        return vesti;
    }

}
