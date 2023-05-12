package ru.jonnykea.project.service.menu;

import ru.jonnykea.project.model.restaurant.Menu;
import ru.jonnykea.project.service.MatcherFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.jonnykea.project.service.dish.DishTestData.dishesMealVillage;
import static ru.jonnykea.project.service.dish.DishTestData.newDishes;
import static ru.jonnykea.project.service.restaurant.RestaurantTestData.sushiCity;

public class MenuTestData {
    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "restaurant", "dishes");
    public static MatcherFactory.Matcher<Menu> MENU_WITH_RESTARAUNT_AND_DISHES_MATCHER =
            MatcherFactory.usingAssertions(Menu.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("restaurant.registered", "dish.created", "dishes.restaurant").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });
    public static final int NOT_FOUND = 10;
    public static final int MENU_ID = 1;

    public static final Menu menuMealVillage = new Menu(1, "меню мясной деревни");
    public static final Menu menuPekin = new Menu(2, "меню пекина");

    static {
        menuMealVillage.setDishes(dishesMealVillage);
    }

    public static Menu getNew() {
        Menu newMenu = new Menu(null, "меню суши сити");
        newMenu.setRestaurant(sushiCity);
        newMenu.setDishes(newDishes);
        return newMenu;
    }

    public static Menu getUpdated() {
        return new Menu(1, "обновленное меню мясной деревни");
    }
}
