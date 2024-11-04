package dat.daos;

import java.util.List;

public interface IDAO<T, D> {
    T create(T t);
    T getById(Long id);
    List<T> getAll();
    T update(T t, Long id);
    void delete(Long id);
}
