package back.model;

public class Administrator extends  User {
    private final String firstName;
    private final String lastName;

    public Administrator(long id, String username, String access, String firstName, String lastName) {
        super(id, username, "administrator", access);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Administrator(User user, String firstName, String lastName) {
        super(user.getId(), user.getUsername(), "administrator", user.getAccess());
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
