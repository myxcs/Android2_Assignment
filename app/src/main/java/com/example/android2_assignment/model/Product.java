package com.example.android2_assignment.model;

public class Product {
    private int masp;
    private String tensp;
    private int giaban;
    private int soluong;
    private String hinhanh;

    public Product(int masp, String tensp, int giaban, int soluong) {
        this.masp = masp;
        this.tensp = tensp;
        this.giaban = giaban;
        this.soluong = soluong;
    }

    public Product(String tensp, int giaban, int soluong) {
        this.tensp = tensp;
        this.giaban = giaban;
        this.soluong = soluong;
    }

    public Product(int masp, String tensp, int giaban, int soluong, String hinhanh) {
        this.masp = masp;
        this.tensp = tensp;
        this.giaban = giaban;
        this.soluong = soluong;
        this.hinhanh = hinhanh;
    }

    public Product(String tensp, int giaban, int soluong, String hinhanh) {
        this.tensp = tensp;
        this.giaban = giaban;
        this.soluong = soluong;
        this.hinhanh = hinhanh;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getGiaban() {
        return giaban;
    }

    public void setGiaban(int giaban) {
        this.giaban = giaban;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
