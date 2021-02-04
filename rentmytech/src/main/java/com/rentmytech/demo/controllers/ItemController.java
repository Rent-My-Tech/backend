package com.rentmytech.demo.controllers;


import com.rentmytech.demo.models.Item;
import com.rentmytech.demo.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController
{
    @Autowired
    private ItemService itemService;

    //Get Requests

    @GetMapping(value = "/items", produces = "application/json") // workings as of 2/4
    public ResponseEntity<?> listAllItems()
    {
        List<Item> myItems = itemService.findAll();
        return new ResponseEntity<>(myItems, HttpStatus.OK);
    }

    @GetMapping(value = "/item/{itemid}", produces = "application/json") // workings as of 2/4
    public ResponseEntity<?> findItemById(@PathVariable long itemid)
    {
        Item item = itemService.findItemById(itemid);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping(value = "/item/name/{itemName}", produces = "application/json") // workings as of 2/4
    public ResponseEntity<?> findItemByName(@PathVariable String itemName)
    {
        Item item = itemService.findItemByName(itemName);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping(value = "/items/namelike/{itemNameSubstring}", produces = "application/json")// workings as of 2/4
    public ResponseEntity<?> listItemNameLike(@PathVariable String itemNameSubstring)
    {
        List<Item> myItems = itemService.findItemByNameLike(itemNameSubstring);
        return new ResponseEntity<>(myItems, HttpStatus.OK);
    }

    //Post Requests
    @PostMapping(value = "/item", consumes = "application/json", produces = "application/json") //working as of 2/4
    public ResponseEntity<?> addNewItem(@Valid @RequestBody Item newItem)
    {
        newItem = itemService.save(newItem);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newItemURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{itemid}")
                .buildAndExpand(newItem.getItemid())
                .toUri();
        responseHeaders.setLocation(newItemURI);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    //Put requests
    @PutMapping(value = "/item/{itemid}", consumes = "application/json") //working as of 2/4
    public ResponseEntity<?> updateItem(@RequestBody Item updateItem, @PathVariable long itemid)
    {
        updateItem.setItemid(itemid);
        itemService.save(updateItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Patch request
    @PatchMapping(value = "/item/{itemid}", consumes = "application/json") // working as of 2/4
    public ResponseEntity<?> updatePatchItem(@RequestBody Item updatePatchItem, @PathVariable long itemid)
    {

        itemService.update(updatePatchItem, itemid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Delete requests
    @DeleteMapping(value = "/item/{itemid}") // working as of 2/4
    public ResponseEntity<?> deleteItemById(@PathVariable long itemid)
    {
        itemService.delete(itemid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
