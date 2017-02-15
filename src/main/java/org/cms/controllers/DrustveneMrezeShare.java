/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.controllers;

import domen.MyAccessToken;
import domen.OAuthToken;
import domen.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import library.facebook.FacebookLib;
import library.linkedin.LinkedInLib;
import library.twitter.TwitterLib;
import org.cms.util.Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import twitter4j.TwitterException;

@Controller
@RequestMapping("/share")
public class DrustveneMrezeShare {

    @RequestMapping(value = "/twitter")
    public ModelAndView shareTwitter(@RequestParam("twitter-link") String link, HttpSession session) {
        System.out.println("Link: " + Util.VEST_LINK + link);
        User user = (User) session.getAttribute("user");
        String token = user.getToken();
        String seckret = user.getTokenSecret();
        MyAccessToken accessToken = new MyAccessToken(token, seckret);
        OAuthToken authToken = new OAuthToken(Util.TWITTER_KEY, Util.TWITTER_SECRET, Util.TWITTER_CALLBACK_URL);
        TwitterLib t = new TwitterLib(authToken, accessToken);
        try {
            t.share(Util.VEST_LINK + link);
        } catch (TwitterException ex) {
            Logger.getLogger(DrustveneMrezeShare.class.getName()).log(Level.SEVERE, null, ex);
        }
        ModelAndView mv = new ModelAndView("redirect:/vesti/prikaz_vesti?vestID=" + link);
        return mv;
    }

    @RequestMapping(value = "/linkedin")
    public ModelAndView shareLinkedInUrl(@RequestParam("linkedin-link") String link, HttpSession session) {
        OAuthToken authToken = new OAuthToken(Util.LINKEDIN_KEY, Util.LINKEDIN_SECRET, Util.LINKEDIN_CALLBACK_URL_SHARE);
        LinkedInLib l = new LinkedInLib(authToken);
        String url = l.getUrl();
        try {
            Map<String, String> mapa = new HashMap<>();
            User user = (User) session.getAttribute("user");
            mapa.put("link-" + user.getId(), link);
            System.out.println("Link: " + link);
            saveParamChanges(Util.PROPS, mapa);
        } catch (Exception ex) {
            Logger.getLogger(DrustveneMrezeLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        ModelAndView mv = new ModelAndView("redirect:" + url);
        return mv;
    }

    @RequestMapping(value = "/callback-linkedin", params = "code")
    public ModelAndView getUserLinkedIn(@RequestParam("code") String code, HttpSession session) {
        OAuthToken authToken = new OAuthToken(Util.LINKEDIN_KEY, Util.LINKEDIN_SECRET, Util.LINKEDIN_CALLBACK_URL_SHARE);
        LinkedInLib l = new LinkedInLib(authToken);
        User user = (User) session.getAttribute("user");
        String link = loadParams(Util.PROPS, "link-" + user.getId());
        ModelAndView mv = new ModelAndView("redirect:/vesti/prikaz_vesti?vestID=" + link);
        try {
            removeKey(Util.PROPS, "link-" + user.getId());
            System.out.println("link je :" + link);
            System.out.println("code " + code);
            l.share(code, Util.VEST_LINK + link);
        } catch (Exception ex) {
            Logger.getLogger(DrustveneMrezeLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mv;
    }

    @RequestMapping(value = "/callback-linkedin", params = {"error", "error_description"})
    public ModelAndView deniedLinkedIn(@RequestParam("error") String error, @RequestParam("error_description") String errorDescription, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String link = loadParams(Util.PROPS, "link-" + user.getId());
        try {
            removeKey(Util.PROPS, "link-" + user.getId());
        } catch (Exception ex) {
            Logger.getLogger(DrustveneMrezeShare.class.getName()).log(Level.SEVERE, null, ex);
        }
        ModelAndView mv = new ModelAndView("redirect:/vesti/prikaz_vesti?vestID=" + link);
        return mv;
    }

    @RequestMapping(value = "/facebook")
    public ModelAndView shareFacebook(@RequestParam("facebook-link") String link, HttpSession session) {
        System.out.println("Link: " + link);
        OAuthToken authToken = new OAuthToken(Util.FACEBOOK_KEY, Util.FACEBOOK_SECRET, Util.FACEBOOK_CALLBACK_URL);
        FacebookLib f = new FacebookLib(authToken);
        link=Util.VEST_LINK+link;
        String callbackUrl = link;
        String shareUrl = f.getShareUrl(link, callbackUrl);
        System.out.println("Share url:" + shareUrl);
        ModelAndView mv = new ModelAndView("redirect:" + shareUrl);
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
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return props.getProperty(key);
    }

    public void saveParamChanges(String name, Map<String, String> mapa) throws Exception {
        try {

            Properties props;
            try (FileInputStream in = new FileInputStream(name)) {
                props = new Properties();
                props.load(in);
            }
            for (Map.Entry<String, String> entrySet : mapa.entrySet()) {
                String key = entrySet.getKey();
                String value = entrySet.getValue();
                props.setProperty(key, value);
            }

            try (FileOutputStream out = new FileOutputStream(name)) {
                props.store(out, null);
            }
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
