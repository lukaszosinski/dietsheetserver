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
import java.util.Map;


@RestController
public class ShoppingListController {

    @Autowired
    Service<Day> dayService;

    @Autowired
    ShoppingListService shoppingListService;

    @GetMapping(value = "/shoppingListForDays")
    public ResponseEntity<ShoppingList> generateShoppingList(@RequestParam List<Long> dayIds) {
        ShoppingList shoppingList = shoppingListService.generateShoppingListForDays(dayIds);
        return new ResponseEntity<>(shoppingList, HttpStatus.OK);
    }

    @GetMapping(value = "/shoppingList/{id}")
    public ResponseEntity<ShoppingList> getShoppingList(@PathVariable("id") long id) {
        ShoppingList shoppingList = shoppingListService.findById(id);
        return new ResponseEntity<>(shoppingList, HttpStatus.OK);
    }

    @GetMapping(value = "/shoppingList")
    public ResponseEntity<List<ShoppingList>> getAllShoppingLists(
            @RequestParam Map<String,String> params,
            @AuthenticationPrincipal User user) {
        List<ShoppingList> shoppingLists = shoppingListService.findAll(user, params);
        return new ResponseEntity<>(shoppingLists, HttpStatus.OK);
    }

    @PostMapping(value = "/shoppingList")
    public ResponseEntity<ShoppingList> saveShoppingList(
            @RequestBody ShoppingList shoppingList,
            @AuthenticationPrincipal User user) {
        shoppingListService.save(shoppingList, user);
        return new ResponseEntity<>(shoppingList, HttpStatus.CREATED);
    }

    @PutMapping(value = "/shoppingList/{id}")
    public ResponseEntity<ShoppingList> updateShoppingList(
            @PathVariable("id") long id,
            @RequestBody ShoppingList shoppingListUpdateData) {
        shoppingListService.update(shoppingListUpdateData, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/shoppingList/{id}")
    public ResponseEntity<Day> deleteShoppingList(@PathVariable("id") long id) {
        shoppingListService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/shoppingList")
    public ResponseEntity<ShoppingList> deleteAllShoppingLists() {
        shoppingListService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
