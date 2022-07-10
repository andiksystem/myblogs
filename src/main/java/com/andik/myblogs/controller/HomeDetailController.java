/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andik.myblogs.controller;

import com.andik.myblogs.entity.Artikel;
import com.andik.myblogs.service.ArtikelService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author andik
 */
@Named
@ViewScoped
public class HomeDetailController implements Serializable {
    
    @EJB
    private ArtikelService artikelService;
    
    private Artikel artikel;
    
    @PostConstruct
    public void init() {
        
        String artikelId = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap().get("artikelId");
        
        artikel = artikelService.findById(artikelId);
        
    }

    public Artikel getArtikel() {
        return artikel;
    }
    
}
