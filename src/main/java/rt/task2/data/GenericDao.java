package rt.task2.data;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {

    T create() throws PersistException;

    T persist(T object) throws PersistException;

    void update(T object) throws PersistException;

    void delete(T object) throws PersistException;

    T getById(Long id) throws PersistException;

    List<T> getAll() throws PersistException;

    List<T> query(String query, Object[] params) throws PersistException;

    String getSelectQuery();
}
