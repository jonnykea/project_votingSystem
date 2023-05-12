package ru.jonnykea.project.repository.dish;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.jonnykea.project.error.NotFoundException;
import ru.jonnykea.project.model.restaurant.Dish;
import ru.jonnykea.project.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
    @Query(value = """
            SELECT d FROM Dish d
            WHERE d.created = CAST(now() as date)
            AND d.restaurant.id=:id
            group by d.name
            """)
    List<Dish> getAllByRestaurantId(int id);
}