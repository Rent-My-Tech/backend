package com.rentmytech.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

// Think about if we need an item rating
@Entity
@Table(name = "itemratings")
public class ItemRating
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long itemratingid;
    private int itemrating;

    @ManyToOne
    @JoinColumn(name = "itemid", nullable = false)
    @JsonIgnoreProperties(value = "itemratings", allowSetters = true)
    private Item item;

//    @ManyToOne
//    @JoinColumn(name = "userid", nullable = false)
//    @JsonIgnoreProperties(value = "itemratings", allowSetters = true)
//    private User user;

    public ItemRating()
    {

    }

    public ItemRating(int itemrating, Item item)
    {
        this.itemrating = itemrating;
        this.item = item;
    }

    public long getItemratingid() {
        return itemratingid;
    }

    public void setItemratingid(long itemratingid) {
        this.itemratingid = itemratingid;
    }

    public int getItemrating() {
        return itemrating;
    }

    public void setItemrating(int itemrating) {
        this.itemrating = itemrating;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
