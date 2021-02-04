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

//    @ManyToOne
//    @JoinColumn(name = "userid")
//    @JsonIgnoreProperties(value = "items", allowSetters = true)
//    private User user;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "item", allowSetters = true)
    private List<ItemRating> itemRatings = new ArrayList<>();

    private String itemname;
    private String itemdescription;
    private String itemimage;
    private int itemcost;

    public Item()
    {

    }

    public Item(String itemname, String itemdescription, String itemimage, int itemcost)
    {
        this.itemname = itemname;
        this.itemdescription = itemdescription;
        this.itemimage = itemimage;
        this.itemcost = itemcost;
    }

    public long getItemid() {
        return itemid;
    }

    public void setItemid(long itemid) {
        this.itemid = itemid;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

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


    public int getItemcost() {
        return itemcost;
    }

    public void setItemcost(int itemcost) {
        this.itemcost = itemcost;
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
