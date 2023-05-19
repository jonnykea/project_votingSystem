package ru.jonnykea.project.repository.restaurant;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.jonnykea.project.model.restaurant.Menu;
import ru.jonnykea.project.repository.BaseRepository;

import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {

    @Query(value = """
            SELECT m
            FROM Menu m
            WHERE m.restaurant.id =:id
            """)
    List<Menu> getByRestaurantId(int id);
}