/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andik.myblogs.service;

import com.andik.myblogs.entity.Artikel;
import com.andik.myblogs.entity.Komentar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author andik
 */
@Stateless
public class KomentarService {
    
    @PersistenceContext(unitName = "myblogsPU")
    private EntityManager em;
    
    public Komentar findById(String id) {
        return em.find(Komentar.class, id);
    }
    
    public List<Komentar> findByArtikel(Artikel artikel) {
        return em.createQuery("SELECT k FROM Komentar k WHERE k.artikel = :artikel ORDER BY k.waktuDibuat DESC")
                .getResultList();
    }
    
    public void create(Komentar komentar) {
        komentar.setId(UUID.randomUUID().toString());
        komentar.setWaktuDibuat(new Date());
        em.persist(komentar);
    }
    
    public void edit(Komentar komentar) {
        em.merge(komentar);
    }
    
    public void remove(Komentar komentar) {
        em.remove(em.merge(komentar));
    }
    
}
