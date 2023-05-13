package ru.jonnykea.project.service.restaurant;

import ru.jonnykea.project.model.restaurant.Restaurant;
import ru.jonnykea.project.service.MatcherFactory;

import java.time.LocalDate;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);
    public static final int NOT_FOUND = 10;
    public static final int RESTAURANT_ID = 1;
    public static final int RESTAURANT_NEW_ID = 3;

    public static final Restaurant meal_village = new Restaurant(RESTAURANT_ID, "мясная деревня", LocalDate.now(), "специализация блюдо русской кухни", "ул. Партизанская, дом 56");
    public static final Restaurant pekin = new Restaurant(RESTAURANT_ID + 1, "пекин", LocalDate.now(), "специализация блюдо китайской кухни", "пр. Интернациональный, дом 51");


    public static Restaurant getNew() {
        return new Restaurant(null, "АМУР", "японская кухня", "Северное шоссе 55");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(2, "ПЕКИН", "специализация блюдо китайской кухни", "пр. Интернациональный, дом 55");
    }
}
