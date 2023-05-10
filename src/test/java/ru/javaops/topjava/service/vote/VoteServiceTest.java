package ru.javaops.topjava.service.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.error.DataConflictException;
import ru.javaops.topjava.error.NotFoundException;
import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.to.VoteToRating;

import java.time.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javaops.topjava.service.UserTestData.ADMIN_ID;
import static ru.javaops.topjava.service.UserTestData.NEW_USER_ID;
import static ru.javaops.topjava.service.menu.MenuTestData.NOT_FOUND;
import static ru.javaops.topjava.service.restaurant.RestaurantTestData.RESTAURANT_ID;
import static ru.javaops.topjava.service.vote.VoteTestData.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class VoteServiceTest {
    @Autowired
    private VoteService service;

    @Test
    void getByIdWithUserAndRestaurant() {
        Vote actual = service.getByIdWithUserAndRestaurant(VOTE_ID);
        VOTE_WITH_RESTARAUNT_AND_USER_MATCHER.assertMatch(actual, voteUser);
    }

    @Test
    void getAllRating() {
        List<VoteToRating> actual = service.getAllRatingForToday();
        VOTE_TO_MATCHER.assertMatch(actual, votesTo);
    }

    @Test
    void getAll() {
        List<Vote> actual = service.getAll();
        VOTE.assertMatch(actual, votes);
    }

    @Test
    void getByIdNotExisted() {
        assertThrows(NotFoundException.class,
                () -> service.getByIdWithUserAndRestaurant(NOT_FOUND));
    }

    @Test
    void create() {
        Vote newVote = getNew();
        Vote created = service.create(newVote, NEW_USER_ID, RESTAURANT_ID + 1);
        int newId = created.id();
        Vote newV = getNew();
        newV.setId(newId);
        VOTE_WITH_RESTARAUNT_AND_USER_MATCHER.assertMatch(created, newV);
        VOTE_WITH_RESTARAUNT_AND_USER_MATCHER.assertMatch(service.getByIdWithUserAndRestaurant(VOTE_ID + 3), newV);
        assertEquals(4, service.countVotesForToday());
    }

    @Test
    void updateAfterTime() {
        Clock clock = Clock.fixed(Instant.parse("2023-05-10T11:01:00.00Z"), ZoneId.of("UTC"));
        assertThrows(DataConflictException.class,
                () -> service.createForTest(getUpdated(), ADMIN_ID, RESTAURANT_ID + 1, clock));
    }

    @Test
    void updateBeforeTime() {
        Clock clock = Clock.fixed(Instant.parse("2023-05-10T10:59:00.00Z"), ZoneId.of("UTC"));
        Vote newVote = getNew();
        Vote created = service.createForTest(newVote, NEW_USER_ID, RESTAURANT_ID + 1, clock);
        int newId = created.id();
        Vote newV = getNew();
        newV.setId(newId);
        VOTE_WITH_RESTARAUNT_AND_USER_MATCHER.assertMatch(created, newV);
        VOTE_WITH_RESTARAUNT_AND_USER_MATCHER.assertMatch(service.getByIdWithUserAndRestaurant(VOTE_ID + 3), newV);
        assertEquals(4, service.countVotesForToday());
    }

    @Test
    public void checkMockingTime() {
        Clock clock = Clock.fixed(Instant.parse("2023-05-10T10:00:00.00Z"), ZoneId.of("UTC"));
        String dateTimeExpected = "2023-05-10T10:00";
        LocalDateTime dateTime = LocalDateTime.now(clock);
        assertEquals(dateTime.toString(), dateTimeExpected);
    }

    @Test
    void delete() {
        service.delete(VOTE_ID);
        assertThrows(NotFoundException.class,
                () -> service.getById(VOTE_ID));
        assertEquals(2, service.countVotesForToday());
    }
}