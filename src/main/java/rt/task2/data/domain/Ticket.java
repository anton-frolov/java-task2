package rt.task2.data.domain;

import java.util.List;

public class Ticket implements AbstractEntity<Long>{

	private Long id;
	private Person sender;
	private List<Person> recipients;
	private String theme;
	private String body;
	

	@Override
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Person getSender() {
		return sender;
	}


	public void setSender(Person sender) {
		this.sender = sender;
	}


	public List<Person> getRecipients() {
		return recipients;
	}


	public void setRecipients(List<Person> recipients) {
		this.recipients = recipients;
	}


	public String getTheme() {
		return theme;
	}


	public void setTheme(String theme) {
		this.theme = theme;
	}


	public String getBody() {
		return body;
	}


	public void setBody(String body) {
		this.body = body;
	}

	
}
