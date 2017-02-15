/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.controllers;

import domen.OAuthToken;
import domen.User;
import javax.servlet.http.HttpSession;
import library.facebook.FacebookLib;
import library.linkedin.LinkedInLib;
import org.cms.util.Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/social-logout")
public class DrustveneMrezeLogout {

    @RequestMapping(value = "/facebook")
    public ModelAndView logoutFacebook(HttpSession session) {
        OAuthToken authToken = new OAuthToken(Util.FACEBOOK_KEY, Util.FACEBOOK_SECRET, Util.FACEBOOK_CALLBACK_URL_ODJAVA);
        FacebookLib f = new FacebookLib(authToken);
        User user = (User) session.getAttribute("user");
        String url = f.getLogoutUrl(user.getToken(), Util.FACEBOOK_CALLBACK_URL_ODJAVA);
        session.removeAttribute("user");
        ModelAndView mv = new ModelAndView("redirect:" + url);
        return mv;
    }

    @RequestMapping(value = "/twitter")
    public ModelAndView logoutTwitter(HttpSession session) {
        session.removeAttribute("user");
        ModelAndView mv = new ModelAndView("redirect:/pocetna");
        return mv;
    }

    @RequestMapping(value = "/linkedin")
    public ModelAndView logoutLinkedinUrl(HttpSession session) {
        OAuthToken authToken = new OAuthToken(Util.LINKEDIN_KEY, Util.LINKEDIN_SECRET, Util.LINKEDIN_CALLBACK_URL_LOGOUT);
        LinkedInLib l = new LinkedInLib(authToken);
        String url = l.getUrl();
        ModelAndView mv = new ModelAndView("redirect:" + url);
        return mv;
    }

    @RequestMapping(value = "/callback-linkedin")
    public ModelAndView logoutLinkedin(@RequestParam("code") String code,HttpSession session) {
        OAuthToken authToken = new OAuthToken(Util.LINKEDIN_KEY, Util.LINKEDIN_SECRET, Util.LINKEDIN_CALLBACK_URL_LOGOUT);
        LinkedInLib l = new LinkedInLib(authToken);
        l.logout(code);
        session.removeAttribute("user");
        System.out.println("Code:"+code);
        ModelAndView mv = new ModelAndView("redirect:/pocetna");
        return mv;
    }
}
