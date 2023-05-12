package ru.jonnykea.project.service.vote;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jonnykea.project.error.DataConflictException;
import ru.jonnykea.project.error.NotFoundException;
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
    private final LocalTime timeToRevote = LocalTime.of(11, 0);
    private final VoteRepository repository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public Vote getByUserId(int userId) {
        return repository.getExistedUserId(userId);
    }

    public void delete(int id) {
        repository.deleteExisted(id);
    }

    @Transactional
    public Vote create(Vote vote, int userId, int restaurantId) {
        boolean isVoted = repository.findByUserId(userId).isPresent();
        if (isVoted) {
            boolean isAllowed = !LocalTime.now().isAfter(timeToRevote);
            if (!isAllowed) {
                throw new DataConflictException("User with id = " + userId + " has voted");
            }
        }
        vote.setUser(userRepository.getReferenceById(userId));
        vote.setRestaurant(restaurantRepository.getExisted(restaurantId));
        return repository.save(vote);
    }

    @Profile("test")
    @Transactional
    public Vote createForTest(Vote vote, int userId, int restaurantId, Clock clock) {
        boolean isVoted = repository.existsById(userId);
        if (isVoted) {
            LocalTime localTime = LocalTime.now(clock);
            boolean isAllowed = !localTime.isAfter(timeToRevote);
            if (!isAllowed) {
                throw new DataConflictException("User with id = " + userId + " has voted");
            }
        }
        vote.setUser(userRepository.getExisted(userId));
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