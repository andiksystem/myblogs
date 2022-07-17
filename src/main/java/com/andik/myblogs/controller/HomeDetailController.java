/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andik.myblogs.controller;

import com.andik.myblogs.entity.Artikel;
import com.andik.myblogs.entity.Komentar;
import com.andik.myblogs.service.ArtikelService;
import com.andik.myblogs.service.KomentarService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
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
    @EJB
    private KomentarService komentarService;
    @Inject
    private UserInfo userInfo;

    private Long jumlahKomentar;
    private List<Komentar> komentars;
    private Komentar komentar;
    private Artikel artikel;

    @PostConstruct
    public void init() {

        String artikelId = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap().get("artikelId");

        artikel = artikelService.findById(artikelId);
        jumlahKomentar = artikelService.countKomentar(artikel);
        komentars = komentarService.findByArtikel(artikel);

        komentar = new Komentar();

    }

    public Artikel getArtikel() {
        return artikel;
    }

    public Long getJumlahKomentar() {
        return jumlahKomentar;
    }

    public List<Komentar> getKomentars() {
        return komentars;
    }

    public void simpanKomentar() {
        try {
            if (komentar.getIsiKomentar() == null) {
                throw new Exception("Isi komentar harus diisi");
            }

            if ("".equals(komentar.getIsiKomentar())) {
                throw new Exception("Isi komentar harus diisi");
            }

            komentar.setArtikel(artikel);
            komentar.setPengguna(userInfo.getCurrentUser());
            komentarService.create(komentar);
            jumlahKomentar = artikelService.countKomentar(artikel);

            komentars.add(komentar);

            komentar = new Komentar();

            FacesContext
                    .getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Komentar sukses dikirim"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public Komentar getKomentar() {
        return komentar;
    }

}
