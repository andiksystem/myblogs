/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andik.myblogs.controller;

import com.andik.myblogs.entity.Artikel;
import com.andik.myblogs.service.ArtikelService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author andik
 */

@Named
@ViewScoped
public class ArtikelList implements Serializable {
    
    @EJB
    private ArtikelService artikelService;
    
    private List<Artikel> artikels;

    private int page;
    private int limit;
    private String filterText;
    
    @PostConstruct
    public void init() {
        page = 0;
        limit = 25;
        filterText = "";
        load();
    }
    
    public void load() {
        artikels = artikelService.findRange(filterText, page, limit);
    }
    
    public void search() {
        page = 0;
        load();
    }
    
    public void loadMore() {
        page++;
        artikels.addAll(artikelService.findRange(filterText, page, limit));
    }

    public List<Artikel> getArtikels() {
        return artikels;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }
    
    public void remove(Artikel artikel) {
        artikelService.remove(artikel);
        artikels.remove(artikel);
    }
    
}
