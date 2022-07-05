/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andik.myblogs.controller;

import com.andik.myblogs.entity.Artikel;
import com.andik.myblogs.entity.Kategori;
import com.andik.myblogs.service.ArtikelService;
import com.andik.myblogs.service.KategoriService;
import java.io.Serializable;
import java.util.List;
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
public class HomeController implements Serializable {

    @EJB
    private KategoriService kategoriService;
    @EJB
    private ArtikelService artikelService;

    private List<Kategori> kategoris;
    private Kategori selectedKategori;

    private String filterText;
    private int page;
    private int limit;
    private List<Artikel> artikels;

    @PostConstruct
    public void init() {

        selectedKategori = null;

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String kategoriId = externalContext.getRequestParameterMap().get("kategoriId");

        if (kategoriId != null) {
            selectedKategori = kategoriService.findById(kategoriId);
        }

        filterText = "";
        page = 0;
        limit = 10;

        kategoris = kategoriService.findAll();

        search();
    }

    public void search() {
        page = 0;
        if (selectedKategori == null) {
            artikels = artikelService.findRange(filterText, page, limit);
        } else {
            artikels = artikelService.findRange(filterText, page, limit, selectedKategori);
        }
    }

    public void loadMore() {
        page++;
        if (selectedKategori == null) {
            artikels.addAll(artikelService.findRange(filterText, page, limit));
        } else {
            artikels.addAll(artikelService.findRange(filterText, page, limit, selectedKategori));            
        }
    }

    public List<Kategori> getKategoris() {
        return kategoris;
    }

    public List<Artikel> getArtikels() {
        return artikels;
    }

    public Kategori getSelectedKategori() {
        return selectedKategori;
    }

    public void setSelectedKategori(Kategori selectedKategori) {
        this.selectedKategori = selectedKategori;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public String getSubText(String text) {
        text = text.replaceAll("<[^>]*>", "");
        if (text.length() > 400) {
            return text.substring(0, 400);
        }
        return text;
    }

}
