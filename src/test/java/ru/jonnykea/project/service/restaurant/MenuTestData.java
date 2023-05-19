package ru.jonnykea.project.service.restaurant;

import ru.jonnykea.project.MatcherFactory;
import ru.jonnykea.project.model.restaurant.Menu;

import static ru.jonnykea.project.service.restaurant.DishTestData.dishesMealVillage;
import static ru.jonnykea.project.service.restaurant.DishTestData.newDishes;

public class MenuTestData {

    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "restaurant", "dishes.menu");
    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER_DISHES = MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "restaurant", "dishes");
    public static final int NOT_FOUND = 10;
    public static final int MENU_ID = 1;
    public static final Menu menuMealVillage = new Menu(1, "меню мясной деревни");

    static {
        menuMealVillage.setDishes(dishesMealVillage);
    }

    public static Menu getNew() {
        Menu newMenu = new Menu(null, "меню помидор");
        newMenu.setDishes(newDishes);
        return newMenu;
    }

    public static Menu getUpdated() {
        Menu updated = new Menu(1, "обновленное меню мясной деревни");
        updated.setDishes(dishesMealVillage);
        return updated;
    }
}