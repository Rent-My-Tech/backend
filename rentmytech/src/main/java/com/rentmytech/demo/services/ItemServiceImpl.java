package com.rentmytech.demo.services;

import com.rentmytech.demo.exceptions.ResourceNotFoundException;
import com.rentmytech.demo.models.Item;
import com.rentmytech.demo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "itemservice")
public class ItemServiceImpl implements ItemService
{
    @Autowired
    private ItemRepository itemRepository;


    @Override
    public List<Item> findAll()
    {
        List<Item> itemList = new ArrayList<>();
        itemRepository.findAll().iterator().forEachRemaining(itemList::add);
        return itemList;
    }

    @Override
    public Item findItemById(long id)
    {
        return itemRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Item id " + id + " not found"));
    }

    @Override
    public Item findItemByName(String name)
    {
        Item item = itemRepository.findByItemname(name);
        if(item == null)
        {
            throw new ResourceNotFoundException("Item " + name + " not found!");
        }
        return item;
    }

    @Override
    public List<Item> findItemByNameLike(String name)
    {
        List<Item> itemList = itemRepository.findByItemnameContainingIgnoreCase(name);
        return itemList;
    }

    @Transactional
    @Override
    public Item save(Item item)
    {
        Item newItem = new Item();
        if(item.getItemid() !=0)
        {
            itemRepository.findById(item.getItemid()).orElseThrow(()-> new ResourceNotFoundException("Item id " + item.getItemid() + " not found"));
            newItem.setItemid(item.getItemid());
        }

        newItem.setItemname(item.getItemname());
        newItem.setItemimage(item.getItemimage());
        newItem.setItemRatings(item.getItemRatings());
        newItem.setItemcost(item.getItemcost());
        newItem.setItemdescription(item.getItemdescription());
        return itemRepository.save(newItem);

    }
    @Transactional
    @Override
    public Item update(Item item, long id)
    {
        Item currentItem = itemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item " + id + " not found"));
        if(item.getItemname() !=null)
        {
            currentItem.setItemname(item.getItemname());
        }

        if (item.getItemimage() != null)
        {
            currentItem.setItemimage(item.getItemimage());
        }

        if(item.getItemname() !=null)
        {
            currentItem.setItemname(item.getItemname());
        }

        if(item.getItemRatings().size() > 0)
        {
            currentItem.setItemRatings(item.getItemRatings());
        }

        if(item.getItemcost() != 0)
        {
            currentItem.setItemcost(item.getItemcost());
        }

        if(item.getItemdescription() !=null)
        {
            currentItem.setItemdescription(item.getItemdescription());
        }

        return itemRepository.save(currentItem);
    }
    @Transactional
    @Override
    public void delete(long id)
    {
    if(itemRepository.findById(id).isPresent())
    {
        itemRepository.deleteById(id);
    }
    else
    {
        throw new ResourceNotFoundException("Item id " + id + " not found");
    }
    }
    @Transactional
    @Override
    public void deleteAllItems()
    {
        itemRepository.deleteAll();
    }
}
