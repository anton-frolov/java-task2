package rt.task2.data;

public class PersistException extends Exception {
    private static final long serialVersionUID = 1L;

    private String messageDetail;

    public PersistException(String message, Throwable cause) {
	super(message, cause);
	this.setMessageDetail(message);
    }

    public PersistException(Throwable cause) {
	super(cause);
    }

    public PersistException(String string) {
	this.setMessageDetail(string);
    }

    public String getMessageDetail() {
	return messageDetail;
    }

    public void setMessageDetail(String messageDetail) {
	this.messageDetail = messageDetail;
    }

}
