package rt.task2.data;

import java.sql.Connection;
import java.sql.SQLException;

public interface DaoFactory<Context> {
	

	public interface DaoCreator<Context> {
        public GenericDao create(Context context);
    }

    public Context getContext() throws PersistException;

    public GenericDao getDao(Context context, Class dtoClass) throws PersistException;
}
