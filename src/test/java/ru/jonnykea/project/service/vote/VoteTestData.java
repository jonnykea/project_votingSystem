package ru.jonnykea.project.service.vote;

import ru.jonnykea.project.MatcherFactory;
import ru.jonnykea.project.model.Vote;
import ru.jonnykea.project.to.vote.VoteToRating;

import java.time.LocalDate;
import java.util.List;

import static ru.jonnykea.project.service.restaurant.RestaurantTestData.meal_village;
import static ru.jonnykea.project.service.restaurant.RestaurantTestData.pekin;
import static ru.jonnykea.project.web.user.UserTestData.admin;
import static ru.jonnykea.project.web.user.UserTestData.newUser;

public class VoteTestData {
    public static final int VOTE_ID = 1;

    public static final LocalDate localDateNow = LocalDate.now();
    public static final MatcherFactory.Matcher<VoteToRating> VOTE_TO_MATCHER = MatcherFactory.usingEqualsComparator(VoteToRating.class);
    public static final MatcherFactory.Matcher<Vote> VOTE = MatcherFactory.usingEqualsComparator(Vote.class);

    public static final Vote voteAdmin = new Vote(VOTE_ID + 1, localDateNow, meal_village, admin);

    public static final VoteToRating voteRatingForMealVillage = new VoteToRating(meal_village.getName(), 2);
    public static final VoteToRating voteRatingForPekin = new VoteToRating(pekin.getName(), 1);
    public static final List<VoteToRating> votesTo = List.of(voteRatingForMealVillage, voteRatingForPekin);

    public static Vote getNew() {
        return new Vote(null, localDateNow, pekin, newUser);
    }

    public static Vote getUpdated() {
        return new Vote(VOTE_ID + 1, localDateNow, meal_village, admin);
    }
}
