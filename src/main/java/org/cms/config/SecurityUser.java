/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.config;

import java.util.ArrayList;
import java.util.Collection;
import org.cms.domen.Korisnik;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Jelena
 */
public class SecurityUser extends Korisnik implements UserDetails {

    public SecurityUser(Korisnik k) {
        if (k != null) {
            this.setKorisnikID(k.getKorisnikID());
            this.setIme(k.getIme());
            this.setPrezime(k.getPrezime());
            this.setUsername(k.getUsername());
            this.setPassword(k.getPassword());
            this.setSlika(k.getSlika());
            this.setUloga(k.getUloga());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (this.getUloga().equals("admin")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        if (this.getUloga().equals("user")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
