package ru.javaops.topjava.util;

import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;
import ru.javaops.topjava.model.NamedEntity;
import ru.javaops.topjava.repository.BaseRepository;

import java.util.List;
import java.util.function.Predicate;

@UtilityClass
public class Util {
    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T value, @Nullable T start, @Nullable T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) < 0);
    }

   public static <T extends NamedEntity , R extends BaseRepository<T>> List<T> getFiltered(Predicate<T> filter, R  repository, int Id) {
        return repository.getAllByRestaurantId(Id).stream()
                .filter(filter)
                .toList();
    }
}