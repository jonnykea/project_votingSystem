package ru.javaops.topjava.repository.vote;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.error.NotFoundException;
import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.repository.BaseRepository;
import ru.javaops.topjava.to.vote.VoteTo;
import ru.javaops.topjava.to.vote.VoteToRating;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.user.id =:userId")
    int deleteByUserId(int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id =:userId")
    Optional<Vote> findByUserId(int userId);

    @EntityGraph(attributePaths = {"user", "restaurant"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT v FROM Vote v WHERE v.id=?1 AND v.date = CAST(now() as date)")
    Optional<Vote> getWithUserAndRestaurant(int id);

    @Query("SELECT COUNT (*) FROM Vote v WHERE v.date = CAST(now() as date)")
    int countVotes();

    @Query("""
            SELECT new ru.javaops.topjava.to.vote.VoteToRating(r.name, cast(count(v.restaurant) as INTEGER)) 
            FROM Vote v 
            LEFT JOIN v.restaurant r 
            WHERE v.date = CAST(now() as date) 
            GROUP by r.name""")
    List<VoteToRating> getRestaurantRating();

    @Query("""
            SELECT new ru.javaops.topjava.to.vote.VoteTo(v.id, r.name, u.name)
            FROM Vote v 
            LEFT JOIN v.restaurant r 
            LEFT JOIN v.user u
            WHERE v.date = CAST(now() as date) 
            GROUP by v.id""")
    List<VoteTo> getAll();

    default Vote getExistedUserId(int userId) {
        return findByUserId(userId).orElseThrow(() -> new NotFoundException("Vote with userId=" + userId + " not found"));
    }
}