/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andik.myblogs.controller;

import com.andik.myblogs.entity.Pengguna;
import com.andik.myblogs.service.PenggunaService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author andik
 */
@Named
@RequestScoped
public class LoginController {

    @EJB
    private PenggunaService penggunaService;

    private String username;
    private String password;

    public void login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            request.login(username, password);
            Pengguna pengguna = penggunaService.findById(username);
            if ("admin".equals(pengguna.getHakAkses())) {
                context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/faces/views/admin/index.xhtml");
            } else {
                context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath());                
            }

        } catch (ServletException ex) {
            try {
                request.logout();
            } catch (ServletException ex1) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex1);
            }
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Login salah"));
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void logout() {
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.invalidateSession();
            externalContext.redirect(externalContext.getRequestContextPath());
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
