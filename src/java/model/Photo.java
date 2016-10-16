package model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rodrigo
 */
public class Photo 
{
    private int id;
    private byte[] data;
    private String subtitle;
    private String author;
    private String local;

    public Photo(byte[] data, String subtitle, String author, String local) {
        this.data = data;
        this.subtitle = subtitle;
        this.author = author;
        this.local = local;
    }
    
    public Photo()
    {
        
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    
}
