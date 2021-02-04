package com.rentmytech.demo.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ItemListId implements Serializable
{
    /**
     * The id of the user
     */
    private long user;

    /**
     * The id of the truck
     */
    private long item;

    /**
     * The default constructor required by JPA
     */
    public ItemListId()
    {
    }

    public void setItem(long item)
    {
        this.item = item;
    }

    /**
     * Getter for the user id
     *
     * @return long the user id
     */
    public long getUser()
    {
        return user;
    }

    /**
     * Setter for the user id
     *
     * @param user the new user id for this object
     */
    public void setUser(long user)
    {
        this.user = user;
    }

    /**
     * Getter for the role id
     *
     * @return long the role id
     */
    public long getItem()
    {
        return item;
    }

    /**
     * The setter for the role id
     *
     * @param item the new role id for this object
     */
    public void setRole(long item)
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
        // boolean temp = (o.getClass() instanceof Class);
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        ItemListId that = (ItemListId) o;
        return user == that.user &&
                item == that.item;
    }

    @Override
    public int hashCode()
    {
        return 37;
    }
}
