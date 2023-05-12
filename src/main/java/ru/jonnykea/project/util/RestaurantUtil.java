package ru.jonnykea.project.util;

import lombok.experimental.UtilityClass;
import ru.jonnykea.project.model.restaurant.Restaurant;
import ru.jonnykea.project.to.restaurant.RestaurantToFrom;

@UtilityClass
public class RestaurantUtil {

    public static Restaurant createNewFromTo(RestaurantToFrom r) {
        return new Restaurant(null, r.getName(), r.getDescription(), r.getAddress());
    }

    public static Restaurant updateFromTo(RestaurantToFrom r) {
        return new Restaurant(r.getId(), r.getName(), r.getDescription(), r.getAddress());
    }
}