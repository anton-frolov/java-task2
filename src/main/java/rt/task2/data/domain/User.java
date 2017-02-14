package rt.task2.data.domain;

public class User implements AbstractEntity<Long> {

    private Long id;
    private String userId;
    private String password;
    private Long personId;
    private String email;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public Long getPersonId() {
	return personId;
    }

    public void setPersonId(Long personId) {
	this.personId = personId;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }
}
