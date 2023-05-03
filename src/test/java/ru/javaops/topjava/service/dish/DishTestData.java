package ru.javaops.topjava.service.dish;

import ru.javaops.topjava.model.restaurant.Dish;
import ru.javaops.topjava.service.MatcherFactory;

import java.util.List;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> Dish_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "created", "restaurant");
    public static final int NOT_FOUND = 10;
    public static final int DISH_ID = 1;


    public static final Dish dishMealVillage1 = new Dish(1, "суп", 150);
    public static final Dish dishMealVillage2 = new Dish(2, "жаркое", 200);
    public static final Dish dishMealVillage3 = new Dish(3, "холодец", 250);
    public static final Dish dishMealVillage4 = new Dish(4, "салат", 150);
    public static final Dish dishPekin1 = new Dish(5, "мясо в кисло-сладком соусе", 400);
    public static final Dish dishPekin2 = new Dish(6, "салат - пекин", 200);
    public static final Dish dishPekin3 = new Dish(7, "рис", 100);

    public static final List<Dish> dishesMealVillage = List.of(dishMealVillage2, dishMealVillage4, dishMealVillage1, dishMealVillage3);
    public static final List<Dish> dishesPekin = List.of(dishPekin1, dishPekin2, dishPekin1);

    public static Dish getNew() {
        return new Dish(null, "сок - 1 литр", 150);
    }

    public static Dish getUpdated() {
        return new Dish(1, "суп", 100);
    }
}
