package rt.task2.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.postgresql.util.PSQLException;

import rt.task2.data.domain.Person;
import rt.task2.data.domain.Ticket;

public class TicketDaoImpl extends AbstractDaoImpl<Ticket, Long> {

	
	private class PersistTicket extends Ticket {
  		
		public void setId(Long id) {
	            super.setId(id);
	    }

	}
	
	private class PersistPerson extends Person {
  		
		public void setId(Long id) {
	            super.setId(id);
	    }

	}
	
	public TicketDaoImpl(Connection connection) {
		super(connection);
	}

	@Override
	public Ticket create() throws PersistException {
		Ticket ticket = new Ticket(); 
		return persist(ticket);
	}

	@Override
	public String getSelectQuery() {
		return "SELECT * FROM public.ticket";
	}

	@Override
	public String getCreateQuery() {
		return "INSERT INTO public.ticket(person_id, theme, body) VALUES(?, ?, ?) RETURNING id;";
	}

	@Override
	public String getUpdateQuery() {
		return "UPDATE public.ticket SET person_id = ?, theme = ?, body = ? WHERE id = ?;";
	}

	@Override
	public String getDeleteQuery() {
		return "DELETE FROM public.ticket WHERE id = ?;";
	}

	@Override
	protected List<Ticket> parseResultSet(ResultSet rs) throws PersistException {
		LinkedList<Ticket> result = new LinkedList<Ticket>();
        try {
            while (rs.next()) {
                PersistTicket ticket = new PersistTicket();
                PersistPerson sender = new PersistPerson();
                ticket.setId(rs.getLong("id"));
                sender.setId(rs.getLong("person_id"));
                ticket.setSender(sender);
                ticket.setTheme(rs.getString("theme"));
                ticket.setBody(rs.getString("body"));
                result.add(ticket);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement statement, Ticket object) throws PersistException {
		try {
			 if(object.getSender()!=null){
				 statement.setLong(1, object.getSender().getId());
			 }else
			 {
				 statement.setNull(1, Types.NULL);
			 }
			 if(object.getTheme()!=null){
				 statement.setString(2, object.getTheme());
			 }else
			 {
				 statement.setNull(2, Types.NULL);
			 }
            if(object.getBody()!=null){
           	statement.setString(3, object.getBody());
            }else
            {
           	statement.setNull(3, Types.NULL);
            }
       } catch (Exception e) {
           throw new PersistException(e);
       }
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement statement, Ticket object) throws PersistException {
		try {
			if(object.getSender()!=null){
				 statement.setLong(1, object.getSender().getId());
			 }else
			 {
				 statement.setNull(1, Types.NULL);
			 }
			 if(object.getTheme()!=null){
				 statement.setString(2, object.getTheme());
			 }else
			 {
				 statement.setNull(2, Types.NULL);
			 }
	         if(object.getBody()!=null){
	          	statement.setString(3, object.getBody());
	         }else
	         {
	          	statement.setNull(3, Types.NULL);
	         } 
	         statement.setLong(4, object.getId()); 
        } catch (Exception e) {
            throw new PersistException(e);
        }
		
	}

	@Override
	public void update(Ticket object) throws PersistException {
		super.update(object);
		if(object.getRecipients()!=null && object.getRecipients().size()>0){
			String sql = "INSERT INTO public.ticket_recipient (person_id, ticket_id)  VALUES(?, ?);";
					
	        try (PreparedStatement statement = connection.prepareStatement(sql);) {
	        	for (Person person : object.getRecipients()) {
	        		try {
	        			statement.setLong(1, person.getId());
		        		statement.setLong(2, object.getId());
		        		statement.execute();
					} catch(PSQLException e){
						if(!e.getSQLState().equals("23505")) //дубликаты пропускаем
						{
							throw new PersistException(e);
						}
					}
	        		catch (Exception e) {
						throw new PersistException(e);
					}
				}
	            statement.close();
	        } catch (Exception e) {
	            throw new PersistException(e);
	        }	
		}
	}

	@Override
	public Ticket getById(Long id) throws PersistException {
		 Ticket ticket  = super.getById(id);
		 if(ticket!=null){
			 DaoFactory<Connection> factory = DaoFactoryImpl.getInstance();
			 GenericDao<Person, Long> personDao = factory.getDao(connection, Person.class);
			 Person person = personDao.getById(ticket.getSender().getId());
			 ticket.setSender(person);
			 String sql = personDao.getSelectQuery()+ " WHERE id in(select person_id from public.ticket_recipient where ticket_id= ?) ORDER BY ID;";
			 Object[] params = {ticket.getId()};
		     List<Person> recipients = personDao.query(sql, params);
			 ticket.setRecipients(recipients);
		 }
		 return ticket;
	      
	}

	@Override
	public List<Ticket> getAll() throws PersistException {
		List<Ticket> tickets = super.getAll();
		if(tickets!=null&&tickets.size()>0){
			DaoFactory<Connection> factory = DaoFactoryImpl.getInstance();
			GenericDao<Person, Long> personDao = factory.getDao(connection, Person.class);
			for (Ticket ticket : tickets) {
				Person person = personDao.getById(ticket.getSender().getId());
				ticket.setSender(person);
				String sql = personDao.getSelectQuery()+ " WHERE id in(select person_id from public.ticket_recipient where ticket_id= ?) ORDER BY ID;";
				Object[] params = {ticket.getId()};
			    List<Person> recipients = personDao.query(sql, params);
				ticket.setRecipients(recipients);
			}
		}
		return tickets;
	}

	@Override
	public List<Ticket> query(String query, Object[] params) throws PersistException {
		List<Ticket> tickets = super.query(query, params);
		if(tickets!=null&&tickets.size()>0){
			DaoFactory<Connection> factory = DaoFactoryImpl.getInstance();
			GenericDao<Person, Long> personDao = factory.getDao(connection, Person.class);
			for (Ticket ticket : tickets) {
				Person person = personDao.getById(ticket.getSender().getId());
				ticket.setSender(person);
				String sql = personDao.getSelectQuery()+ " WHERE id in(select person_id from public.ticket_recipient where ticket_id= ?) ORDER BY ID;";
				Object[] params2 = {ticket.getId()};
			    List<Person> recipients = personDao.query(sql, params2);
				ticket.setRecipients(recipients);
			}	
		}
		return tickets;
	}


	
}
