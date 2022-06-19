/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andik.myblogs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author andik
 */

@Named
@ApplicationScoped
public class ImageStreamer {
    
    public StreamedContent getFile() throws IOException {
        String uploadPath = "c:/youtube/uploads/images";
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            String filename = context.getExternalContext().getRequestParameterMap().get("filename");
            File file = new File(uploadPath, filename);
            String contentType = Files.probeContentType(file.toPath());
            InputStream inputStream = new FileInputStream(file);
            return DefaultStreamedContent.builder()
                    .contentType(contentType)
                    .name(filename)
                    .stream(() -> inputStream)
                    .build();
        }
    }
    
}
