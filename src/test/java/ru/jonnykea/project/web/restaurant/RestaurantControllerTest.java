package ru.jonnykea.project.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.jonnykea.project.model.restaurant.Restaurant;
import ru.jonnykea.project.service.restaurant.RestaurantService;
import ru.jonnykea.project.to.restaurant.RestaurantToFrom;
import ru.jonnykea.project.util.JsonUtil;
import ru.jonnykea.project.util.RestaurantUtil;
import ru.jonnykea.project.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.jonnykea.project.service.restaurant.RestaurantTestData.*;
import static ru.jonnykea.project.web.user.UserTestData.ADMIN_MAIL;
import static ru.jonnykea.project.web.user.UserTestData.USER_MAIL;

class RestaurantControllerTest extends AbstractControllerTest {
    private static final String REST_URL = RestaurantController.REST_URL + '/';

    @Autowired
    private RestaurantService service;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(meal_village));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        RestaurantToFrom newTo = new RestaurantToFrom(null, "АМУР", "японская кухня", "Северное шоссе 55");
        Restaurant newRestaurant = RestaurantUtil.createNewFromTo(newTo);
        ResultActions action = perform(MockMvcRequestBuilders.post("/api/admin/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(service.get(newId), newRestaurant);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        RestaurantToFrom newTo = new RestaurantToFrom(null, null, null, null);

        perform(MockMvcRequestBuilders.post("/api/admin/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void  createDuplicate() throws Exception {
        RestaurantToFrom newTo = new RestaurantToFrom(null, "пекин", "специализация блюдо китайской кухни", "пр. Интернациональный, дом 51");
        Restaurant updated = RestaurantUtil.updateFromTo(newTo);
        perform(MockMvcRequestBuilders.post("/api/admin/restaurants").contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        RestaurantToFrom newTo = new RestaurantToFrom(2, "ПЕКИН", "специализация блюдо китайской кухни", "пр. Интернациональный, дом 55");
        Restaurant updated = RestaurantUtil.updateFromTo(newTo);
        perform(MockMvcRequestBuilders.put("/api/admin/restaurants/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(service.get(RESTAURANT_ID + 1), updated);
    }
}