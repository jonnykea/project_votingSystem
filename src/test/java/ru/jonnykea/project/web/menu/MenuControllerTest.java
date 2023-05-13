package ru.jonnykea.project.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.jonnykea.project.model.restaurant.Menu;
import ru.jonnykea.project.service.menu.MenuService;
import ru.jonnykea.project.service.restaurant.RestaurantTestData;
import ru.jonnykea.project.to.restaurant.MenuTo;
import ru.jonnykea.project.util.JsonUtil;
import ru.jonnykea.project.util.MenuUtil;
import ru.jonnykea.project.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.jonnykea.project.service.menu.MenuTestData.*;
import static ru.jonnykea.project.service.restaurant.RestaurantTestData.RESTAURANT_ID;
import static ru.jonnykea.project.web.user.UserTestData.ADMIN_MAIL;
import static ru.jonnykea.project.web.user.UserTestData.USER_MAIL;

class MenuControllerTest extends AbstractControllerTest {
    private static final String REST_URL = AdminMenuController.REST_URL;

    @Autowired
    private MenuService service;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get("/api/restaurants/id/menu/" + RESTAURANT_ID + "/with-dishes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menuMealVillage));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        MenuTo newTo = new MenuTo(null, "меню помидор", RESTAURANT_ID + 2);
        Menu newMenu = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        Menu created = MENU_MATCHER.readFromJson(action);
        int newId = created.id();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(service.getByRestaurantId(RestaurantTestData.RESTAURANT_NEW_ID), newMenu);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        MenuTo newTo = new MenuTo(null, null, null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicate() throws Exception {
        MenuTo newTo = new MenuTo(null, "меню пекина", 2);
        Menu updated = MenuUtil.updateFromTo(newTo);
        perform(MockMvcRequestBuilders.post(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        MenuTo newTo = new MenuTo(1, "обновленное меню мясной деревни", 1);
        Menu updated = MenuUtil.updateFromTo(newTo);
        perform(MockMvcRequestBuilders.put(REST_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        MENU_MATCHER_DISHES.assertMatch(updated, service.getByRestaurantId(RestaurantTestData.RESTAURANT_ID));
    }
}