/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andik.myblogs.controller;

import com.andik.myblogs.entity.Artikel;
import com.andik.myblogs.entity.Kategori;
import com.andik.myblogs.service.ArtikelService;
import com.andik.myblogs.service.KategoriService;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
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
public class ArtikelEdit implements Serializable {
    
    @EJB
    private ArtikelService artikelService;
    @EJB
    private KategoriService kategoriService;
    
    private List<Kategori> kategoris;
    
    private Artikel artikel;
    
    @PostConstruct
    public void init() {
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        artikel = artikelService.findById(id);
        kategoris = kategoriService.findAll();
    }
    
    public void save() {
        try {
            artikelService.edit(artikel);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "/faces/views/admin/artikel/list.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ArtikelEdit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Artikel getArtikel() {
        return artikel;
    }

    public List<Kategori> getKategoris() {
        return kategoris;
    }
    
}
