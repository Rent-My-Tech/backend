package com.rentmytech.demo.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "items")
public class Item extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(name = "itemid", value = "primary key for item")
    private long itemid;

    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties("projects")
    private User user;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "item", allowSetters = true)
    private List<ItemImage> itemImages = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "item", allowSetters = true)
    private List<ItemRating> itemRatings = new ArrayList<>();

    private String itemname;
    private String itemdescription;
    private String owner;
    private String itemimage;
    private int itemcost;

    public Item()
    {

    }

    public Item(User user, String itemname, String itemdescription, String owner, String projectimage, int itemcost)
    {
        this.user = user;
        this.itemname = itemname;
        this.itemdescription = itemdescription;
        this.owner = owner;
        this.itemimage = projectimage;
        this.itemcost = itemcost;
    }


    public Item(String itemname, int itemcost)
    {
        super();
    }

    public Item(String itemname, int itemcost, String owner, User newUser)
    {
        super();
    }

    public long getItemid() {
        return itemid;
    }

    public void setItemid(long itemid) {
        this.itemid = itemid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemdescription() {
        return itemdescription;
    }

    public void setItemdescription(String itemdescription) {
        this.itemdescription = itemdescription;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getProjectimage() {
        return itemimage;
    }

    public void setProjectimage(String projectimage) {
        this.itemimage = projectimage;
    }

    public int getItemcost() {
        return itemcost;
    }

    public void setItemcost(int itemcost) {
        this.itemcost = itemcost;
    }

    public List<ItemImage> getItemImages() {
        return itemImages;
    }

    public void setItemImages(List<ItemImage> itemImages) {
        this.itemImages = itemImages;
    }

    public List<ItemRating> getItemRatings() {
        return itemRatings;
    }

    public void setItemRatings(List<ItemRating> itemRatings) {
        this.itemRatings = itemRatings;
    }

    public String getItemimage() {
        return itemimage;
    }

    public void setItemimage(String itemimage) {
        this.itemimage = itemimage;
    }
}
