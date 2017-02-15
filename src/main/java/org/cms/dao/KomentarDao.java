/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.dao;

import java.util.List;
import org.cms.domen.Kategorija;
import org.cms.domen.Komentar;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class KomentarDao {

    @Autowired
    private SessionFactory sessionFactory;

    public KomentarDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void sacuvajKomentar(Komentar komentar) {
        sessionFactory.getCurrentSession().save(komentar);
    }

    @Transactional
    public List<Komentar> vratiKomentareVesti(int vestID) {
        Session session = this.sessionFactory.getCurrentSession();
        Query q = session.createQuery("from Komentar where vest_id=:id order by datum desc");
        q.setParameter("id", vestID);
        List<Komentar> komentari = q.list();
        return komentari;
    }

}
