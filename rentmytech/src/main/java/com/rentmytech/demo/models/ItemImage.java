package com.rentmytech.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "itemimages")
public class ItemImage
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long itemimageid;

    private String imageURL;

    @ManyToOne
    @JoinColumn(name = "itemid", nullable = false)
    @JsonIgnoreProperties(value = "itemimages", allowSetters = true)
    private Item item;

    public ItemImage()
    {

    }

    public ItemImage(String imageURL, Item item)
    {
        this.imageURL = imageURL;
        this.item = item;
    }

    public long getItemimageid() {
        return itemimageid;
    }

    public void setItemimageid(long itemimageid) {
        this.itemimageid = itemimageid;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}


