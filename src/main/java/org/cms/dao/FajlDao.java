/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.dao;

import org.cms.domen.Fajl;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Bojan
 */
@Repository
public class FajlDao {

    @Autowired
    private SessionFactory sessionFactory;

    public FajlDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void sacuvajFajl(Fajl fajl) {
        sessionFactory.getCurrentSession().persist(fajl);
    }

    @Transactional
    public int vratiID() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Fajl.class).setProjection(Projections.max("fajlID"));
        try {
            int id = (int) criteria.uniqueResult();
            return id;
        } catch (Exception e) {
            return 0;
        }
    }
}
