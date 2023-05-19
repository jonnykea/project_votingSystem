package ru.jonnykea.project.repository.restaurant;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.jonnykea.project.model.restaurant.Dish;
import ru.jonnykea.project.repository.BaseRepository;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
    @Query(value = """
            SELECT d FROM Dish d
            JOIN FETCH d.menu
            WHERE d.menu.restaurant.id=:restaurantId
            AND d.menu.created=:date
            group by d.id
            """)
    List<Dish> getAllByDate(int restaurantId, LocalDate date);
}