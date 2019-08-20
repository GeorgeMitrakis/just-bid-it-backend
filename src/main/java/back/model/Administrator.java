package back.model;

public class Administrator extends  User {
    private final String firstname;
    private final String lastname;

    public Administrator(long id, String username, String access, String firstname, String lastname) {
        super(id, username, "administrator", access);
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Administrator(User user, String firstname, String lastname) {
        super(user.getId(), user.getUsername(), "administrator", user.getAccess());
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
