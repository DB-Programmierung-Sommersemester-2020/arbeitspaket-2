package buchlager.repositories.services;

import java.util.Collection;

public interface Repository<T, K> {
    T getById(K id);
    
    boolean create(T entity);
    boolean update(T entity);
    boolean delete(T entity);

    Collection<T> getAll();
}