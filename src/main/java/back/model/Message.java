package back.model;

public class Message {
    private int id;
    private String sender; //username
    private String receiver; //username
    private String text;
    private String time;

    public Message(int id, String sender, String receiver, String text, String time) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
