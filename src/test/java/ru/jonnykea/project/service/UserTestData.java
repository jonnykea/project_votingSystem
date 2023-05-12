package ru.jonnykea.project.service;

import ru.jonnykea.project.model.user.Role;
import ru.jonnykea.project.model.user.User;
import ru.jonnykea.project.util.JsonUtil;

import java.util.Collections;
import java.util.Date;

public class UserTestData {

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int GUEST_ID = 3;
    public static final int NEW_USER_ID = 4;
    public static final int NOT_FOUND = 100;
    public static final String USER_MAIL = "user@gmail.com";
    public static final String ADMIN_MAIL = "admin@javaops.ru";
    public static final String GUEST_MAIL = "guest@gmail.com";
    public static final String NEW_USER_MAIL = "new@gmail.com";

    public static final User user = new User(USER_ID, "User", USER_MAIL, "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", Role.ADMIN, Role.USER);
    public static final User guest = new User(GUEST_ID, "Guest", GUEST_MAIL, "{noop}guest");
    public static final User newUser = new User(NEW_USER_ID, "NewUser", NEW_USER_MAIL, "{noop}new");

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "UpdatedName", USER_MAIL, "newPass", false, new Date(), Collections.singleton(Role.ADMIN));
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}