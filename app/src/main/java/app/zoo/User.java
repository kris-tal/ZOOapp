package app.zoo;

public class User {
    private int userID;
    private String role;
    private boolean permissions;

    public User(int userID, String role) {
        this.userID = userID;
        this.role = role;
        this.permissions = role.equals("zarządca");
    }

    public int getID() {
        return userID;
    }

    public String getRole() {
        return role;
    }

    public boolean getPermissions() {
        return permissions;
    }
}
