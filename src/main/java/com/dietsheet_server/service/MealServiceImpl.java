package com.dietsheet_server.service;


import com.dietsheet_server.DAO.MealDAO;
import com.dietsheet_server.DAO.QueryParams;
import com.dietsheet_server.model.diet.DietEntityCascadeUpdater;
import com.dietsheet_server.model.diet.Ingredient;
import com.dietsheet_server.model.diet.Meal;
import com.dietsheet_server.model.user.User;
import com.dietsheet_server.model.diet.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service("mealService")
@Transactional(propagation = Propagation.REQUIRED)
public class MealServiceImpl implements Service<Meal> {

    @Autowired
    private MealDAO mealDAO;

    @Autowired
    private Service<Product> productService;

    @Autowired
    private DietEntityCascadeUpdater dietEntityCascadeUpdater;

    @Override

    public Meal findById(long id) {
        Meal meal = mealDAO.get(id);
        if(meal == null) {
            throw new ResourceNotFoundException();
        }
        return meal;
    }

    @Override
    public void save(Meal meal) {
        if(isExist(meal)) {
            throw new DataIntegrityViolationException("Resource exists");
        }
        meal.updateIngredients(getInitializedIngredients(meal.getIngredients()));
        meal.recalculateSummary();
        mealDAO.save(meal);
    }



    @Override
    public void save(Meal meal, User owner) {
        meal.setOwner(owner);
        save(meal);
    }

    @Override
    public void update(Meal mealUpdateData, long id) {
        Meal mealToUpdate = findById(id);
        mealToUpdate.setName(mealUpdateData.getName());
        mealToUpdate.updateIngredients(
                getInitializedIngredients(
                    mealUpdateData.getIngredients()
        ));
        mealToUpdate.setDescription(mealUpdateData.getDescription());
        mealToUpdate.recalculateSummary();
        mealDAO.update(mealToUpdate);
        dietEntityCascadeUpdater.cascadeUpdateParents(mealToUpdate);
    }


    @Override
    public void delete(Meal meal) {
        mealDAO.delete(meal);
    }

    @Override
    public void delete(long id) {
        Meal meal = findById(id);
        delete(meal);
    }

    @Override
    public List<Meal> findAll() {
        return mealDAO.getAll();
    }

    @Override
    public List<Meal> findAll(List<Long> ids) {
        return mealDAO.getByIds(ids);
    }

    @Override
    public List<Meal> findAll(User user, Map<String, String> params) {
        QueryParams queryParams = new QueryParams(params);
        return mealDAO.getAllByUser(user, queryParams);
    }

    @Override
    public void deleteAll() {
        mealDAO.deleteAll();
    }

    @Override
    public boolean isExist(Meal meal) {
        try {
            mealDAO.get(meal.getId());
            return true;
        } catch (ResourceNotFoundException e) {
            return false;
        }
    }

    private List<Ingredient> getInitializedIngredients(List<Ingredient> ingredients) {
        return ingredients
                .stream()
                .map(ingredient -> new Ingredient(
                            productService.findById(ingredient.getProduct().getId()),
                            ingredient.getAmount()
                        )
                )
                .collect(Collectors.toList());
    }
}
