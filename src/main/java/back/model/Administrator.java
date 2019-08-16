package back.model;

public class Administrator extends  User {
    private final String firstName;
    private final String lastName;

    public Administrator(long id, String username, String role, String access, String firstName, String lastName) {
        super(id, username, role, access);
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
