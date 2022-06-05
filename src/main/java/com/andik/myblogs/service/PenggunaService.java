/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andik.myblogs.service;

import com.andik.myblogs.entity.Pengguna;
import com.google.common.hash.Hashing;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author andik
 */
@Stateless
public class PenggunaService {
    
    @PersistenceContext(unitName = "myblogsPU")
    private EntityManager em;
    
    public Pengguna findById(String id) {
        return em.find(Pengguna.class, id);
    }
    
    public List<Pengguna> findAll() {
        return em.createQuery("SELECT k FROM Pengguna k ORDER BY k.nama ASC")
                .getResultList();
    }
    
    public void create(Pengguna pengguna) {
        String password = Hashing.sha256().hashString(pengguna.getPassword(), Charset.defaultCharset()).toString();
        pengguna.setPassword(password);
        pengguna.setWaktuDibuat(new Date());
        em.persist(pengguna);
    }
    
    public void ubahPassword(Pengguna pengguna, String newPassword) {
        String password = Hashing.sha256().hashString(newPassword, Charset.defaultCharset()).toString();
        pengguna.setPassword(password);        
    }
    
    public void edit(Pengguna pengguna) {
        em.merge(pengguna);
    }
    
    public void remove(Pengguna pengguna) {
        em.remove(em.merge(pengguna));
    }
    
}
