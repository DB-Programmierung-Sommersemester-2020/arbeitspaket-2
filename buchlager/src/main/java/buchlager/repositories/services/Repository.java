package buchlager.repositories.services;

import java.util.Collection;

public interface Repository<T, K> {
    T getById(K id);
    
    boolean create();
    boolean update();
    boolean delete();

    Collection<T> getAll();
}