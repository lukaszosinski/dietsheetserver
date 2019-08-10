package com.dietsheet_server.controller;
import com.dietsheet_server.model.ShoppingList;
import com.dietsheet_server.model.diet.Day;
import com.dietsheet_server.service.Service;
import com.dietsheet_server.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ShoppingListController {

    @Autowired
    Service<Day> dayService;

    @Autowired
    ShoppingListService shoppingListService;

    @GetMapping(value = "/shoppinglist")
    public ResponseEntity<ShoppingList> generateShoppingList(@RequestParam List<Long> dayIdList) {
        List<Day> days = dayService.findAll(dayIdList);
        ShoppingList shoppingList = shoppingListService.generateShoppingListForDays(days);
        return new ResponseEntity<>(shoppingList, HttpStatus.OK);
    }



}
