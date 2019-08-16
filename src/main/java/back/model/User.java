package back.model;

public class User {

    private long id;
    private final String username;
    private final String role;
    private final String access;

    public User(long id, String username, String role, String access) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.access = access;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getAccess() {
        return access;
    }

    public void setId(long id){
        this.id = id;
    }

    //TODO: method for changing access to "granted" or "denied"
}
