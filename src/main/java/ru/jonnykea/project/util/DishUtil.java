package ru.jonnykea.project.util;

import lombok.experimental.UtilityClass;
import ru.jonnykea.project.model.restaurant.Dish;
import ru.jonnykea.project.to.restaurant.DishTo;

@UtilityClass
public class DishUtil {

    public static Dish createNewFromTo(DishTo dishTo) {
        return new Dish(null, dishTo.getName(),dishTo.getPrice());
    }

    public static Dish updateFromTo(DishTo dishTo) {
        return new Dish(dishTo.getId(), dishTo.getName(), dishTo.getPrice());
    }
}