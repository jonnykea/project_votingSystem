package ru.jonnykea.project.service.vote;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jonnykea.project.error.DataConflictException;
import ru.jonnykea.project.model.Vote;
import ru.jonnykea.project.repository.restaurant.RestaurantRepository;
import ru.jonnykea.project.repository.user.UserRepository;
import ru.jonnykea.project.repository.vote.VoteRepository;
import ru.jonnykea.project.to.vote.VoteTo;
import ru.jonnykea.project.to.vote.VoteToRating;

import java.time.Clock;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Service
public class VoteService {
    private static final LocalTime TIME_TO_REVOTE = LocalTime.of(11, 0);
    private final Clock clock;

    private final VoteRepository repository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;


    public Vote getByUserId(int userId) {
        return repository.getExistedUserId(userId);
    }

    @Transactional
    public Vote save(Vote vote, int userId, int restaurantId) {
        boolean isVoted = repository.findByUserId(userId).isPresent();
        if (isVoted) {
            boolean isAllowed = !LocalTime.now(clock).isAfter(TIME_TO_REVOTE);
            if (!isAllowed) {
                throw new DataConflictException("User with id = " + userId + " has voted");
            }
        }
        vote.setUser(userRepository.getReferenceById(userId));
        vote.setRestaurant(restaurantRepository.getExisted(restaurantId));
        return repository.save(vote);
    }

    public int getCount() {
        return repository.countVotes();
    }

    public List<VoteToRating> getRating() {
        return repository.getRestaurantRating();
    }

    public List<VoteTo> getAll() {
        return repository.getAll();
    }
}