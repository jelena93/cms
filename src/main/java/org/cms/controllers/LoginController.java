/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("admin_home");
        return mv;
    }

}
