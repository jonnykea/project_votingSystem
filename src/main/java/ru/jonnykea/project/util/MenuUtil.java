package ru.jonnykea.project.util;

import lombok.experimental.UtilityClass;
import ru.jonnykea.project.model.restaurant.Menu;
import ru.jonnykea.project.to.restaurant.MenuTo;

@UtilityClass
public class MenuUtil {

    public static Menu createNewFromTo(MenuTo menuTo) {
        return new Menu(null, menuTo.getName(), menuTo.getCreated());
    }

    public static Menu updateFromTo(MenuTo menuTo) {
        return new Menu(menuTo.getId(), menuTo.getName(),menuTo.getCreated());
    }
}