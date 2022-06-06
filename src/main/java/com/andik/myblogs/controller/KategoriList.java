/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andik.myblogs.controller;

import com.andik.myblogs.entity.Kategori;
import com.andik.myblogs.service.KategoriService;
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
public class KategoriList implements Serializable {
    
    @EJB
    private KategoriService kategoriService;
    
    private List<Kategori> kategoris;

    private String filterText;
    
    @PostConstruct
    public void init() {
        filterText = "";
        load();
    }
    
    public void load() {
        kategoris = kategoriService.findAll();
    }
    
    public void search() {
        load();
    }

    public List<Kategori> getKategoris() {
        return kategoris;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }
    
}
