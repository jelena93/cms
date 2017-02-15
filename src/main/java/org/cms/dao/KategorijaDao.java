/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.dao;

import java.util.List;
import org.cms.domen.Kategorija;
import org.cms.domen.Vest;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Vukasin
 */
@Repository
public class KategorijaDao {

    @Autowired
    private SessionFactory sessionFactory;

    public KategorijaDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Kategorija> vratiRootKategorije() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Kategorija> kategorije = session.createQuery("from Kategorija where podkategorijaid is null").list();
        return kategorije;
    }

    @Transactional
    public List<Kategorija> vratiListKategorije() {
        Session session = this.sessionFactory.getCurrentSession();
        List list = session.createSQLQuery("SELECT t1.* FROM\n"
                + "kategorija AS t1 LEFT JOIN kategorija AS t2\n"
                + "ON t1.kategorijaID = t2.podkategorijaID\n"
                + "WHERE t2.kategorijaID IS NULL;\n"
                + "").addEntity(Kategorija.class).list();
//        for (Object l : list) {
//            System.out.println(l);
//        }
        return list;
    }

    @Transactional
    public List<Kategorija> vratiKategorije() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Kategorija> kategorije = session.createQuery("from Kategorija").list();
        return kategorije;
    }

    @Transactional
    public Kategorija vratiKategoriju(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Kategorija kategorija = (Kategorija) session.load(Kategorija.class, id);
        return kategorija;
    }

    @Transactional
    public void sacuvajKategoriju(Kategorija kategorija) {
        sessionFactory.getCurrentSession().save(kategorija);
    }

    @Transactional
    public void izmeniKategoriju(Kategorija kategorija) {
        sessionFactory.getCurrentSession().update(kategorija);
    }

    @Transactional
    public int vratiID() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Kategorija.class).setProjection(Projections.max("kategorijaID"));
        try {
            int id = (int) criteria.uniqueResult();
            return id;
        } catch (Exception e) {
            return 0;
        }
    }

}
