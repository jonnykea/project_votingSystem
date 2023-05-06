package ru.javaops.topjava.repository.vote;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.repository.BaseRepository;
import ru.javaops.topjava.to.VoteToRating;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @EntityGraph(attributePaths = {"user", "restaurant"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT v FROM Vote v WHERE v.id=?1 AND v.date = CAST(now() as date)")
    Optional<Vote> getWithUserAndRestaurant(int id);

    @EntityGraph(attributePaths = {"user", "restaurant"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT v FROM Vote v WHERE v.date = CAST(now() as date)")
    List<Vote> getAll();

    @Query("SELECT COUNT (*) FROM Vote v WHERE v.date = CAST(now() as date)")
    int countVotes();

 /*   @Query(value = """
            select r.NAME as name, COUNT(RESTAURANT_ID) as rating
            FROM VOTE
                     left join RESTAURANT R on R.ID = VOTE.RESTAURANT_ID
            WHERE DATE = CAST(now() as date)
            group by r.NAME
            """, nativeQuery = true)
    List<VoteToRating> getRestaurantRating();*/

 /*   """
            SELECT r.name, COUNT(v.restaurant) as rating
            FROM Vote v
            LEFT JOIN v.restaurant r ON r.id
            WHERE v.date = CAST(now() as date)
            GROUP by r.name
            """*/
    @Query("""
            SELECT new ru.javaops.topjava.to.VoteToRating(r.name, cast(count(v.restaurant) as INTEGER)) 
            FROM Vote v 
            LEFT JOIN v.restaurant r 
            WHERE v.date = CAST(now() as date) 
            GROUP by r.name""")
    List<VoteToRating> getRestaurantRating();
}