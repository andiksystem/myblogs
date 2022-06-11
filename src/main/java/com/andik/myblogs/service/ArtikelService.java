/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andik.myblogs.service;

import com.andik.myblogs.entity.Artikel;
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
public class ArtikelService {
    
    @PersistenceContext(unitName = "myblogsPU")
    private EntityManager em;
    
    public Artikel findById(String id) {
        return em.find(Artikel.class, id);
    }
    
    public List<Artikel> findRange(String filterText, int page, int limit) {
        return em.createQuery("SELECT k FROM Artikel k WHERE LOWER(k.judul) LIKE LOWER(:filterText) ORDER BY k.judul ASC")
                .setParameter("filterText", "%" + filterText + "%")
                .setFirstResult(page * limit)
                .setMaxResults(limit)
                .getResultList();
    }
    
    public void create(Artikel artikel) {
        artikel.setId(UUID.randomUUID().toString());
        artikel.setWaktuDibuat(new Date());
        em.persist(artikel);
    }
    
    public void edit(Artikel artikel) {
        em.merge(artikel);
    }
    
    public void remove(Artikel artikel) {
        em.remove(em.merge(artikel));
    }
    
}
