package com.rentmytech.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "myitemlist")
@IdClass(ItemListId.class)
public class ItemList extends Auditable implements Serializable
{
// -------- Association Fields ------------
    /**
     * 1/2 of the primary key (long) for userroles.
     * Also is a foreign key into the users table
     */
    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = "truck",
            allowSetters = true)
    private User user;

    /**
     * 1/2 of the primary key (long) for userroles.
     * Also is a foreign key into the roles table
     */
    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "itemid")
    @JsonIgnoreProperties(value = "user",
            allowSetters = true)
    private Item item;

    public ItemList()
    {
    }

    public ItemList(
            @NotNull User user,
            @NotNull Item item)
    {
        this.user = user;
        this.item = item;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Item getItem()
    {
        return item;
    }

    public void setItem(Item item)
    {
        this.item = item;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof ItemList))
        {
            return false;
        }
        ItemList that = (ItemList) o;
        return ((user == null) ? 0 : user.getUserid()) == ((that.user == null) ? 0 : that.user.getUserid()) &&
                ((item == null) ? 0 : item.getItemid()) == ((that.item == null) ? 0 : that.item.getItemid());
    }

    @Override
    public int hashCode()
    {
        // return Objects.hash(user.getUserid(), role.getRoleid());
        return 37;
    }
}
