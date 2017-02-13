package rt.task2.data;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {
	
	public T create() throws PersistException;

	public T persist(T object)  throws PersistException;
	
	public void update(T object) throws PersistException;
	
	public void delete(T object) throws PersistException;
	
	public T getById(Long id) throws PersistException;
	
	public List<T> getAll() throws PersistException;
	
	public List<T> query(String query, Object[] params) throws PersistException;
	
	public String getSelectQuery();

}
