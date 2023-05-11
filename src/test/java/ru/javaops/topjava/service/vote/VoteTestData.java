package ru.javaops.topjava.service.vote;

import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.service.MatcherFactory;
import ru.javaops.topjava.to.vote.VoteToRating;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javaops.topjava.service.UserTestData.*;
import static ru.javaops.topjava.service.restaurant.RestaurantTestData.meal_village;
import static ru.javaops.topjava.service.restaurant.RestaurantTestData.pekin;

public class VoteTestData {
    public static final int VOTE_ID = 1;

    public static final LocalDate localDateNow = LocalDate.now();
    public static final MatcherFactory.Matcher<VoteToRating> VOTE_TO_MATCHER = MatcherFactory.usingEqualsComparator(VoteToRating.class);
    public static final MatcherFactory.Matcher<Vote> VOTE = MatcherFactory.usingEqualsComparator(Vote.class);
    public static MatcherFactory.Matcher<Vote> VOTE_WITH_RESTARAUNT_AND_USER_MATCHER =
            MatcherFactory.usingAssertions(Vote.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("date", "user.registered", "restaurant.registered").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });
    public static final Vote voteUser = new Vote(VOTE_ID, localDateNow, pekin, guest);
    public static final Vote voteAdmin = new Vote(VOTE_ID + 1, localDateNow, meal_village, admin);
    public static final Vote voteGuest = new Vote(VOTE_ID + 2, localDateNow, meal_village, user);

    public static final VoteToRating voteRatingForMealVillage = new VoteToRating(meal_village.getName(), 2);
    public static final VoteToRating voteRatingForPekin = new VoteToRating(pekin.getName(), 1);
    public static final List<VoteToRating> votesTo = List.of(voteRatingForMealVillage, voteRatingForPekin);
    public static final List<Vote> votes = List.of(voteUser, voteAdmin, voteGuest);

    public static Vote getNew() {
        return new Vote(null, localDateNow, pekin, newUser);
    }

    public static Vote getUpdated() {
        return new Vote(VOTE_ID + 1, localDateNow, meal_village, admin);
    }
}
