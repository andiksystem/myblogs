/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andik.myblogs.controller;

import com.andik.myblogs.entity.Pengguna;
import com.andik.myblogs.service.PenggunaService;
import java.io.Serializable;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author andik
 */

@Named
@ViewScoped
public class SignUp implements Serializable {
    
    @EJB
    private PenggunaService penggunaService;
    
    private Pengguna pengguna;
    
    private String password1;
    private String password2;
    
    @PostConstruct
    public void init() {
        pengguna = new Pengguna();
        password1 = "";
        password2 = "";
    }

    public Pengguna getPengguna() {
        return pengguna;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
    
    public void save() {
        try {
            
            Pengguna temp = penggunaService.findById(pengguna.getId());
            if (temp != null) {
                throw new Exception("Username " + pengguna.getId() + " sudah ada");
            }
            
            if (!Objects.equals(password1, password2)) {
                throw new Exception("Password belum sama");
            }
            
            pengguna.setPassword(password1);
            pengguna.setHakAkses("user");
            penggunaService.create(pengguna);
            
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "/faces/login.xhtml");
            
        } catch (Exception e) {
            
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            
        }
    }
    
}
