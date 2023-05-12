package ru.jonnykea.project.util.validation;

import lombok.experimental.UtilityClass;
import ru.jonnykea.project.HasId;
import ru.jonnykea.project.error.DataConflictException;
import ru.jonnykea.project.error.IllegalRequestDataException;

@UtilityClass
public class ValidationUtil {

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    //  Conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    public static void checkOwner(int id, int idForUpdate) {
        if (id != idForUpdate) {
            throw new DataConflictException("entity with " + id + " belong to other");
        }
    }
}