package ru.jonnykea.project.service.restaurant;

import ru.jonnykea.project.model.restaurant.Restaurant;
import ru.jonnykea.project.service.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "registered");
    public static final int NOT_FOUND = 10;
    public static final int RESTAURANT_ID = 1;
    public static final int RESTAURANT_NEW_ID = 3;

    public static final Restaurant meal_village = new Restaurant(RESTAURANT_ID, "мясная деревня", "специализация блюдо русской кухни", "ул. Партизанская, дом 56");
    public static final Restaurant pekin = new Restaurant(RESTAURANT_ID + 1, "пекин", "специализация блюдо китайской кухни", "пр. Интернациональный, дом 51");
    public static final Restaurant sushiCity = new Restaurant(RESTAURANT_NEW_ID, "суши сити", LocalDate.of(2023, 5, 1), "специализация блюдо японской кухни", "пр. Ленина, дом 10");

    public static final List<Restaurant> restaurants = List.of(meal_village, pekin);
    public static Restaurant getNew() {
        return new Restaurant(null, "АМУР", "японская кухня","Северное шоссе 55");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(2, "ПЕКИН", "специализация блюдо китайской кухни", "пр. Интернациональный, дом 55");
    }
}
