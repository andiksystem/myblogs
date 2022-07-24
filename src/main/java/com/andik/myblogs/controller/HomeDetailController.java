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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    
    private Map<String, List<Komentar>> jawabanMap;
    
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
        
        jawabanMap = new LinkedHashMap<>();
        for (Komentar kom : komentars) {
            List<Komentar> jawabans = komentarService.findJawabanKomentar(kom);
            jawabanMap.put(kom.getId(), jawabans);
        }

        komentar = new Komentar();
        komentar.setJenis(0);

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
            jawabanMap.put(komentar.getId(), new ArrayList<>());

            komentar = new Komentar();
            komentar.setJenis(0);
            

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

    public Map<String, List<Komentar>> getJawabanMap() {
        return jawabanMap;
    }
    
    public void createJawabanKomentar(Komentar ref) {
        Komentar kom = new Komentar();
        kom.setJenis(1);
        kom.setRefKomentarId(ref.getId());
        kom.setArtikel(artikel);
        kom.setPengguna(userInfo.getCurrentUser());
        jawabanMap.get(ref.getId()).add(kom);
    }
    
    public void simpanJawabanKomentar(Komentar jawaban) {
        komentarService.create(jawaban);
        jumlahKomentar = artikelService.countKomentar(artikel);
    }

}
