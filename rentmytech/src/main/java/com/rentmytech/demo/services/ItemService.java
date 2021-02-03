package com.rentmytech.demo.services;

import com.rentmytech.demo.models.Item;

import java.util.List;

public interface ItemService
{
    List<Item> findAll();

    Item findItemById(long id);

    Item findItemByName(String name);

    List<Item> findItemByNameLike(String name);

    Item save(Item item);

    Item update(Item item, long id);

    void delete(long id);

    void deleteAllItems();
}
