package ru.javaops.topjava.service.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.error.DataConflictException;
import ru.javaops.topjava.error.NotFoundException;
import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.repository.restaurant.RestaurantRepository;
import ru.javaops.topjava.repository.user.UserRepository;
import ru.javaops.topjava.repository.vote.VoteRepository;
import ru.javaops.topjava.to.vote.VoteTo;
import ru.javaops.topjava.to.vote.VoteToRating;

import java.time.Clock;
import java.time.LocalTime;
import java.util.List;

@Service
public class VoteService {
    private final LocalTime timeToRevote = LocalTime.of(11, 0);
    private final VoteRepository repository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public VoteService(VoteRepository repository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Vote getByUserId(int userId) {
        return repository.getExistedUserId(userId);
    }

    public Vote getByIdWithUserAndRestaurant(int id) {
        return repository.getWithUserAndRestaurant(id).orElseThrow(() ->
                new NotFoundException("Vote with id=" + id + " not found"));
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