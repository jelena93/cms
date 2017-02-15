/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.controllers;

import domen.MyRequestToken;
import domen.OAuthToken;
import facebook4j.FacebookException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import library.linkedin.LinkedInLib;
import library.twitter.TwitterLib;
import library.facebook.FacebookLib;
import org.cms.util.Util;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import twitter4j.TwitterException;

@Controller
@RequestMapping("/login")
public class DrustveneMrezeLogin {

    @RequestMapping(value = "/twitter", method = RequestMethod.GET)
    public ModelAndView loginTwitter(HttpSession session) {
        try {
            OAuthToken authToken = new OAuthToken(Util.TWITTER_KEY, Util.TWITTER_SECRET, Util.TWITTER_CALLBACK_URL);
            TwitterLib t = new TwitterLib(authToken);
            String url = t.login();
            ModelAndView mv = new ModelAndView("redirect:" + url);
            return mv;
        } catch (TwitterException ex) {
            Logger.getLogger(DrustveneMrezeLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ModelAndView("redirect:login");
    }

    @RequestMapping(value = "/callback-twitter", params = {"oauth_token", "oauth_verifier"})
    public ModelAndView getUserTwitter(@RequestParam("oauth_token") String oauthToken, @RequestParam("oauth_verifier") String oauthVerifier,
            HttpSession session) {
        ModelAndView mv = new ModelAndView("redirect:/pocetna");
        try {
            OAuthToken authToken = new OAuthToken(Util.TWITTER_KEY, Util.TWITTER_SECRET, Util.TWITTER_CALLBACK_URL);
            TwitterLib t = new TwitterLib(authToken);
            domen.User user = t.getUser(oauthToken, oauthVerifier);
            System.out.println("User: " + user);
            session.setAttribute("user", user);
        } catch (TwitterException ex) {
            Logger.getLogger(DrustveneMrezeLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mv;
    }

    @RequestMapping(value = "/callback-twitter", params = "denied")
    public ModelAndView deniedTwitter(@RequestParam("denied") String denied) {
        ModelAndView mv = new ModelAndView("redirect:/login");
        return mv;
    }

    @RequestMapping(value = "/linkedin", method = RequestMethod.GET)
    public ModelAndView loginLinkedIn(HttpSession session) {
        OAuthToken authToken = new OAuthToken(Util.LINKEDIN_KEY, Util.LINKEDIN_SECRET, Util.LINKEDIN_CALLBACK_URL);
        LinkedInLib l = new LinkedInLib(authToken);
        String url = l.getUrl();
        ModelAndView mv = new ModelAndView("redirect:" + url);
        return mv;
    }

    @RequestMapping(value = "/callback-linkedin", params = {"code"})
    public ModelAndView getUserLinkedIn(@RequestParam("code") String code, HttpSession session) {
        ModelAndView mv = new ModelAndView("redirect:/pocetna");
        OAuthToken authToken = new OAuthToken(Util.LINKEDIN_KEY, Util.LINKEDIN_SECRET, Util.LINKEDIN_CALLBACK_URL);
        LinkedInLib l = new LinkedInLib(authToken);
        try {
            domen.User user = l.getUser(code);
            session.setAttribute("user", user);
            System.out.println(user);
            System.out.println("Code:"+code);
        } catch (ParseException ex) {
            Logger.getLogger(DrustveneMrezeLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DrustveneMrezeLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mv;
    }

    @RequestMapping(value = "/callback-linkedin", params = {"error","error_description","state"})
    public ModelAndView deniedLinkedIn(@RequestParam("error") String error) {
        ModelAndView mv = new ModelAndView("redirect:/login");
        return mv;
    }

    @RequestMapping(value = "/facebook", method = RequestMethod.GET)
    public ModelAndView loginFacebook(HttpSession session) {
        OAuthToken authToken = new OAuthToken(Util.FACEBOOK_KEY, Util.FACEBOOK_SECRET, Util.FACEBOOK_CALLBACK_URL);
        FacebookLib f = new FacebookLib(authToken);
        String url = f.getUrl();
        ModelAndView mv = new ModelAndView("redirect:" + url);
        return mv;
    }

    @RequestMapping(value = "/callback-facebook", method = RequestMethod.GET)
    public ModelAndView getUserFacebook(@RequestParam("code") String code, HttpSession session) {
        ModelAndView mv = new ModelAndView("redirect:/pocetna");
        OAuthToken authToken = new OAuthToken(Util.FACEBOOK_KEY, Util.FACEBOOK_SECRET, Util.FACEBOOK_CALLBACK_URL);
        FacebookLib f = new FacebookLib(authToken);
        try {
            domen.User user = f.getUser(code);
            System.out.println(user);
            session.setAttribute("user", user);
        } catch (FacebookException ex) {
            Logger.getLogger(DrustveneMrezeLogin.class.getName()).log(Level.SEVERE, null, ex);
        }

        return mv;

    }

    public String loadParams(String name, String key) {
        Properties props = new Properties();
        InputStream is;
        try {
            File f = new File(name);
            is = new FileInputStream(f);
        } catch (Exception e) {
            is = null;
        }
        try {
            if (is == null) {
                is = getClass().getResourceAsStream(name);
            }
            props.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return props.getProperty(key);
    }

    public void saveParamChanges(String name, String key, String value) throws Exception {
        try {
            Properties prop = new Properties();
            prop.put(key, value);
            File f = new File(name);
            OutputStream out = new FileOutputStream(f);
            prop.store(out, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeKey(String name, String key) throws Exception {
        try {
            Properties prop = new Properties();
            prop.remove(key);
            File f = new File(name);
            OutputStream out = new FileOutputStream(f);
            prop.store(out, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
