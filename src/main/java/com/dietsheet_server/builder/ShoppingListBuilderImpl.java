package com.dietsheet_server.builder;

import com.dietsheet_server.model.ShoppingList;
import com.dietsheet_server.model.ShoppingListItem;
import com.dietsheet_server.model.diet.Ingredient;
import com.dietsheet_server.model.diet.Product;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ShoppingListBuilderImpl implements ShoppingListBuilder {

    private Map<Product, Integer> ingredientAmountMap = new HashMap<>();

    @Override
    public ShoppingListBuilder addItem(Ingredient ingredient) {
        if (ingredientAmountMap.containsKey(ingredient.getProduct())) {
            ingredientAmountMap.put(ingredient.getProduct(), ingredientAmountMap.get(ingredient.getProduct()) + ingredient.getAmount());
        }
        else {
            ingredientAmountMap.put(ingredient.getProduct(), ingredient.getAmount());
        }
        return this;
    }

    @Override
    public ShoppingList build() {
        return new ShoppingList(getShoppingListItemList());
    }

    private List<ShoppingListItem> getShoppingListItemList() {
        final List<ShoppingListItem> shoppingListItemList = new ArrayList<>();
        ingredientAmountMap.forEach((product, amount) -> shoppingListItemList.add(new ShoppingListItem(product, amount)));
        return shoppingListItemList;
    }
}
