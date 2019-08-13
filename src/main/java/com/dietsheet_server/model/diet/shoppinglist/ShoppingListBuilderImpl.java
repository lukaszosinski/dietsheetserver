package com.dietsheet_server.model.diet.shoppinglist;

import com.dietsheet_server.model.diet.Ingredient;
import com.dietsheet_server.model.diet.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingListBuilderImpl implements ShoppingListBuilder {

    private Map<Product, Integer> ingredientAmountMap = new HashMap<>();

    @Override
    public ShoppingListBuilder addItem(Ingredient ingredient) {
        if (ingredientAmountMap.containsKey(ingredient.getProduct())) {
            ingredientAmountMap.put(
                    ingredient.getProduct(),
                    ingredientAmountMap.get(ingredient.getProduct()) + ingredient.getAmount()
            );
        }
        else {
            ingredientAmountMap.put(
                    ingredient.getProduct(),
                    ingredient.getAmount()
            );
        }
        return this;
    }

    @Override
    public ShoppingList build() {
        return new ShoppingList(getIngredientList());
    }

    private List<Ingredient> getIngredientList() {
        final List<Ingredient> ingredientList = new ArrayList<>();
        ingredientAmountMap.forEach((product, amount) -> ingredientList.add(new Ingredient(product, amount)));
        return ingredientList;
    }
}
