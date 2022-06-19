/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andik.myblogs.controller;

import com.andik.myblogs.entity.Artikel;
import com.andik.myblogs.entity.Kategori;
import com.andik.myblogs.service.ArtikelService;
import com.andik.myblogs.service.KategoriService;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;

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
    
    private UploadedFile uploadedFile;

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public void handleUpload(FileUploadEvent event) {
        try {
            List<String> fileAllowedList = Arrays.asList("jpg", "jpeg", "png");
            if (!fileAllowedList.contains(FilenameUtils.getExtension(event.getFile().getFileName()).toLowerCase())) {
                throw new Exception("Format gambar tidak diijinkan");
            }
            
            // validasi
            
            String uploadPath = "c:/youtube/uploads/images";
            
            UploadedFile file = event.getFile();
            File temp = new File(uploadPath);
            if (!temp.exists()) {
                temp.mkdirs();
            }
            
            Path folder = Paths.get(uploadPath);
            String ext = FilenameUtils.getExtension(file.getFileName());
            Path path = Files.createTempFile(folder, UUID.randomUUID().toString(), "." + ext);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            
            File namaGambar = new File(uploadPath, path.getFileName().toString());
            artikel.setGambar(namaGambar.getName());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }
    
    private static final Logger LOG = Logger.getLogger(ArtikelEdit.class.getName());
    
}
