/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andik.myblogs.controller;

import com.andik.myblogs.entity.Kategori;
import com.andik.myblogs.service.KategoriService;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
public class KategoriCreate implements Serializable {
    
    @EJB
    private KategoriService kategoriService;
    
    private Kategori kategori;
    
    @PostConstruct
    public void init() {
        kategori = new Kategori();
    }
    
    public void save() {
        try {
            kategoriService.create(kategori);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "/faces/views/admin/kategori/list.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(KategoriCreate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Kategori getKategori() {
        return kategori;
    }
    
}
