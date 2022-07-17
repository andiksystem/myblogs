/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andik.myblogs.controller;

import com.andik.myblogs.entity.Pengguna;
import com.andik.myblogs.service.PenggunaService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author andik
 */
@Named
@SessionScoped
public class UserInfo implements Serializable {
    
    private Pengguna currentUser;
    
    @EJB
    private PenggunaService penggunaService;
    
    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        currentUser = penggunaService.findById(externalContext.getUserPrincipal().getName());
    }

    public Pengguna getCurrentUser() {
        return currentUser;
    }
    
}
