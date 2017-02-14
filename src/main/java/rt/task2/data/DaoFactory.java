package rt.task2.data;

public interface DaoFactory<Context> {

    public interface DaoCreator<Context> {
	GenericDao create(Context context);
    }

    Context getContext() throws PersistException;

    GenericDao getDao(Context context, Class dtoClass) throws PersistException;
}
