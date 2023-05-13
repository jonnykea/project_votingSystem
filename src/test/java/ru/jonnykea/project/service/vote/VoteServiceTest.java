package ru.jonnykea.project.service.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.jonnykea.project.error.DataConflictException;
import ru.jonnykea.project.error.NotFoundException;
import ru.jonnykea.project.model.Vote;
import ru.jonnykea.project.to.vote.VoteToRating;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.jonnykea.project.service.menu.MenuTestData.NOT_FOUND;
import static ru.jonnykea.project.service.restaurant.RestaurantTestData.RESTAURANT_ID;
import static ru.jonnykea.project.service.vote.VoteTestData.*;
import static ru.jonnykea.project.web.user.UserTestData.ADMIN_ID;
import static ru.jonnykea.project.web.user.UserTestData.NEW_USER_ID;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class VoteServiceTest {
    @Autowired
    private VoteService service;

    @Test
    void getByIdWithUserAndRestaurant() {
        Vote actual = service.getByUserId(VOTE_ID + 1);
        VOTE.assertMatch(actual, voteAdmin);
    }

    @Test
    void getAllRating() {
        List<VoteToRating> actual = service.getRating();
        VOTE_TO_MATCHER.assertMatch(actual, votesTo);
    }

    @Test
    void getByIdNotExisted() {
        assertThrows(NotFoundException.class,
                () -> service.getByUserId(NOT_FOUND));
    }

    @Test
    void create() {
        Vote newVote = getNew();
        Vote created = service.create(newVote, NEW_USER_ID, RESTAURANT_ID + 1);
        int newId = created.id();
        Vote newV = getNew();
        newV.setId(newId);
        VOTE.assertMatch(created, newV);
        VOTE.assertMatch(service.getByUserId(VOTE_ID + 3), newV);
        assertEquals(4, service.getCount());
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
        VOTE.assertMatch(created, newV);
        VOTE.assertMatch(service.getByUserId(VOTE_ID + 3), newV);
        assertEquals(4, service.getCount());
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
        assertThrows(NotFoundException.class, () -> service.getByUserId(VOTE_ID + 2));
        assertEquals(2, service.getCount());
    }
}