package com.rentmytech.demo.repository;

import com.rentmytech.demo.models.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long>
{
    Item findByItemname(String name);
    List<Item> findByItemnameContainingIgnoreCase(String name);
}
