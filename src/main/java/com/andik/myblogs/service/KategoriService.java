/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andik.myblogs.service;

import com.andik.myblogs.entity.Kategori;
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
public class KategoriService {
    
    @PersistenceContext(unitName = "myblogsPU")
    private EntityManager em;
    
    public Kategori findById(String id) {
        return em.find(Kategori.class, id);
    }
    
    public List<Kategori> findAll() {
        return em.createQuery("SELECT k FROM Kategori k ORDER BY k.nama ASC")
                .getResultList();
    }
    
    public void create(Kategori kategori) {
        kategori.setId(UUID.randomUUID().toString());
        kategori.setWaktuDibuat(new Date());
        em.persist(kategori);
    }
    
    public void edit(Kategori kategori) {
        em.merge(kategori);
    }
    
    public void remove(Kategori kategori) {
        em.remove(em.merge(kategori));
    }
    
}
