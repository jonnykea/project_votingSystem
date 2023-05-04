package ru.javaops.topjava.service.menu;

import ru.javaops.topjava.model.restaurant.Dish;
import ru.javaops.topjava.model.restaurant.Menu;
import ru.javaops.topjava.service.MatcherFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javaops.topjava.service.dish.DishTestData.dishesMealVillage;
import static ru.javaops.topjava.service.restaurant.RestaurantTestData.meal_village;

public class MenuTestData {
    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "restaurant", "dishes");
    public static MatcherFactory.Matcher<Menu> MENU_WITH_RESTARAUNT_AND_DISHES_MATCHER =
            MatcherFactory.usingAssertions(Menu.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("restaurant.registered", "dish.created").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });
    public static final int NOT_FOUND = 10;
    public static final int MENU_ID = 1;

    public static final Menu menuMealVillage = new Menu(1, "меню мясной деревни");
    public static final Menu menuMealPekin = new Menu(2, "меню пекина");

    static {
        menuMealVillage.setDishes(dishesMealVillage);
        menuMealVillage.setRestaurant(meal_village);
    }
    public static Menu getNew() {
        return new Menu(null, "меню суши сити");
    }

    public static Menu getUpdated() {
        return new Menu(1, "обновленное меню мясной деревни");
    }
}
