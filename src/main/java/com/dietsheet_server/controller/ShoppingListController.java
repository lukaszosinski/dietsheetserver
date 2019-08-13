package com.dietsheet_server.controller;
import com.dietsheet_server.model.diet.shoppinglist.ShoppingList;
import com.dietsheet_server.model.User;
import com.dietsheet_server.model.diet.Day;
import com.dietsheet_server.service.Service;
import com.dietsheet_server.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ShoppingListController {

    @Autowired
    Service<Day> dayService;

    @Autowired
    ShoppingListService shoppingListService;

    @GetMapping(value = "/shoppingListForDays")
    public ResponseEntity<ShoppingList> generateShoppingList(@RequestParam List<Long> dayIds) {
        List<Day> days = dayService.findAll(dayIds);
        ShoppingList shoppingList = shoppingListService.generateShoppingListForDays(days);
        return new ResponseEntity<>(shoppingList, HttpStatus.OK);
    }

    @GetMapping(value = "/shoppingList/{id}")
    public ResponseEntity<ShoppingList> getShoppingList(@PathVariable("id") long id) {
        ShoppingList shoppingList = shoppingListService.findById(id);
        return new ResponseEntity<>(shoppingList, HttpStatus.OK);
    }

    @GetMapping(value = "/shoppingList")
    public ResponseEntity<List<ShoppingList>> getAllShoppingLists(@AuthenticationPrincipal User user) {
        List<ShoppingList> shoppingLists = shoppingListService.findAll(user);
        if(shoppingLists.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(shoppingLists, HttpStatus.OK);
    }

    @PostMapping(value = "/shoppingList")
    public ResponseEntity<ShoppingList> saveShoppingList(
            @RequestBody ShoppingList shoppingList,
            @AuthenticationPrincipal User user) {
        if (shoppingListService.isExist(shoppingList)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        shoppingList.setOwner(user);
        shoppingListService.save(shoppingList);
        return new ResponseEntity<>(shoppingList, HttpStatus.CREATED);
    }

    @PutMapping(value = "/shoppingList/{id}")
    public ResponseEntity<ShoppingList> updateShoppingList(
            @PathVariable("id") long id,
            @RequestBody ShoppingList shoppingList) {
        ShoppingList shoppingListToUpdate = shoppingListService.findById(id);
        shoppingListToUpdate.updateItems(shoppingList.getItems());
        shoppingListService.update(shoppingListToUpdate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/shoppingList/{id}")
    public ResponseEntity<Day> deleteShoppingList(@PathVariable("id") long id) {
        ShoppingList shoppingList = shoppingListService.findById(id);
        shoppingListService.delete(shoppingList);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/shoppingList")
    public ResponseEntity<ShoppingList> deleteAllShoppingLists() {
        shoppingListService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
