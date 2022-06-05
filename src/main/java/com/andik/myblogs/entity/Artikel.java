/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andik.myblogs.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author andik
 */
@Entity
@Table(name = "artikel")
@NamedQueries({
    @NamedQuery(name = "Artikel.findAll", query = "SELECT a FROM Artikel a")})
public class Artikel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "id")
    private String id;
    @Size(max = 255)
    @Column(name = "judul")
    private String judul;
    @Size(max = 2147483647)
    @Column(name = "isi")
    private String isi;
    @Size(max = 255)
    @Column(name = "gambar")
    private String gambar;
    @Column(name = "waktu_dibuat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date waktuDibuat;
    @Column(name = "is_published")
    private Boolean isPublished;
    @Column(name = "waktu_publish")
    @Temporal(TemporalType.TIMESTAMP)
    private Date waktuPublish;
    @Column(name = "waktu_diubah")
    @Temporal(TemporalType.TIMESTAMP)
    private Date waktuDiubah;
    @JoinColumn(name = "kategori_id", referencedColumnName = "id")
    @ManyToOne
    private Kategori kategori;
    @JoinColumn(name = "pengguna_id", referencedColumnName = "id")
    @ManyToOne
    private Pengguna pengguna;
    @OneToMany(mappedBy = "artikel")
    private Collection<Komentar> komentarCollection;

    public Artikel() {
    }

    public Artikel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public Date getWaktuDibuat() {
        return waktuDibuat;
    }

    public void setWaktuDibuat(Date waktuDibuat) {
        this.waktuDibuat = waktuDibuat;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public Date getWaktuPublish() {
        return waktuPublish;
    }

    public void setWaktuPublish(Date waktuPublish) {
        this.waktuPublish = waktuPublish;
    }

    public Date getWaktuDiubah() {
        return waktuDiubah;
    }

    public void setWaktuDiubah(Date waktuDiubah) {
        this.waktuDiubah = waktuDiubah;
    }

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }

    public Pengguna getPengguna() {
        return pengguna;
    }

    public void setPengguna(Pengguna pengguna) {
        this.pengguna = pengguna;
    }

    public Collection<Komentar> getKomentarCollection() {
        return komentarCollection;
    }

    public void setKomentarCollection(Collection<Komentar> komentarCollection) {
        this.komentarCollection = komentarCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artikel)) {
            return false;
        }
        Artikel other = (Artikel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.andik.myblogs.entity.Artikel[ id=" + id + " ]";
    }
    
}
