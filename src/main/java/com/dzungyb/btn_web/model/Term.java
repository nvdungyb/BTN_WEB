package com.dzungyb.btn_web.model;

import jakarta.persistence.*;

import java.util.Date;
@Entity
@Table(name = "term")
public class Term {
    @Id

    private int id;
    @Column(name = "ten", nullable = false)
    private String ten;
    @Column(name = "ngay", nullable = false)
    private String ngay;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    @Override
    public String toString() {
        return "Term{" +
                "id=" + id +
                ", ten='" + ten + '\'' +
                ", ngay=" + ngay +
                '}';
    }
}
