package app.zoo;

public class User {
    private String userID;
    private String role;
    private boolean permissions;

    public User(String userID, String role) {
        this.userID = userID;
        this.role = role;
        this.permissions = role.equals("zarządca");
    }

    public String getID() {
        return userID;
    }

    public String getRole() {
        return role;
    }

    public boolean getPermissions() {
        return permissions;
    }
}
