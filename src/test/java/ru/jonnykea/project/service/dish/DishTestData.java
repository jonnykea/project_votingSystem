package ru.jonnykea.project.service.dish;

import ru.jonnykea.project.MatcherFactory;
import ru.jonnykea.project.model.restaurant.Dish;

import java.time.LocalDate;
import java.util.List;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant", "dishes.restaurant");
    public static final int NOT_FOUND = 100;
    public static final int DISH_ID = 1;

    public static final Dish dishMealVillage1 = new Dish(1, "суп", LocalDate.now(), 150);
    public static final Dish dishMealVillage2 = new Dish(2, "жаркое", LocalDate.now(), 200);
    public static final Dish dishMealVillage3 = new Dish(3, "холодец", LocalDate.now(), 250);
    public static final Dish dishMealVillage4 = new Dish(4, "салат", LocalDate.now(), 150);
    public static final Dish newDish1 = new Dish(11, "пицца бургер", LocalDate.now(), 450);
    public static final Dish newDish2 = new Dish(12, "пицца 4 сыры", LocalDate.now(), 1000);

    public static final List<Dish> dishesMealVillage = List.of(dishMealVillage2, dishMealVillage4, dishMealVillage1, dishMealVillage3);
    public static final List<Dish> newDishes = List.of(newDish2, newDish1);

    public static Dish getNew() {
        return new Dish(null, "сок - 1 литр", 150);
    }

    public static Dish getUpdated() {
        return new Dish(1, "суп", 100);
    }
}
