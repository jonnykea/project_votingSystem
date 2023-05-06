package ru.javaops.topjava.service.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.error.DontAllowVoteException;
import ru.javaops.topjava.error.NotFoundException;
import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.repository.restaurant.RestaurantRepository;
import ru.javaops.topjava.repository.user.UserRepository;
import ru.javaops.topjava.repository.vote.VoteRepository;
import ru.javaops.topjava.to.VoteToRating;

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

    public Vote getById(int id) {
        return repository.getExisted(id);
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
        boolean isVoted = repository.existsById(userId);
        if (isVoted) {
            boolean isAllowVote = !LocalTime.now().isAfter(timeToRevote);
            if (!isAllowVote) {
                throw new DontAllowVoteException("User with id = " + userId + " has voted");
            }
        }
        vote.setUser(userRepository.getExisted(userId));
        vote.setRestaurant(restaurantRepository.getExisted(restaurantId));
        return repository.save(vote);
    }

    public int countVotesForToday(){
        return  repository.countVotes();
    }

    public List<VoteToRating> getAllRatingForToday() {
        return repository.getRestaurantRating();
    }

    public List<Vote> getAll() {
        return repository.getAll();
    }
}