/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.controllers;

import org.cms.dao.VestDao;
import org.cms.domen.Statistika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Bojan
 */
@Controller
@RequestMapping("/admin/")
public class StatistikaController {
    @Autowired
    private VestDao vestDao;
    
    @RequestMapping("/statistika")
    public ModelAndView vratiStatistiku(){
        System.out.println("Vracanje parametara****");
        Statistika[] statistike = vestDao.vratiParametreZaStatistiku();
        ModelAndView mav = new ModelAndView("statistika", "statistike", statistike);
        return mav;
    }
}
