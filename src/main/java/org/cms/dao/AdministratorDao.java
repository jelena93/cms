/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class AdministratorDao {

    @Autowired
    private SessionFactory sessionFactory;

    public AdministratorDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


//    @Transactional
//    public Administrator proveriAdmina(String user, String pass) {
//        Session session = this.sessionFactory.getCurrentSession();
//        Query q =session.createQuery("from Administrator where username=:u and password=:p");
//        q.setParameter("u",user );
//        q.setParameter("p",pass);
//        Administrator admin = (Administrator) q.uniqueResult();
//        return admin;
//    }

//    @Transactional
//    public Administrator proveriAdmina(String user) {
//        Session session = this.sessionFactory.getCurrentSession();
//        Query q =session.createQuery("from Administrator where username=:u");
//        q.setParameter("u",user );
//        Administrator admin = (Administrator) q.uniqueResult();
//        return admin;
//    }
}
