package ru.javaops.topjava.util;

import lombok.experimental.UtilityClass;
import ru.javaops.topjava.model.restaurant.Menu;
import ru.javaops.topjava.to.restaurant.MenuTo;

@UtilityClass
public class MenuUtil {

    public static Menu createNewFromTo(MenuTo menuTo) {
        return new Menu(null, menuTo.getName());
    }

    public static Menu updateFromTo(MenuTo menuTo) {
        return new Menu(menuTo.getId(), menuTo.getName());
    }
}