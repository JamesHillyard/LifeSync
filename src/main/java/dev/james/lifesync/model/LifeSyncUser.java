package dev.james.lifesync.model;

public class LifeSyncUser {

    private int id;
    private String firstName;
    private String lastName;

    public LifeSyncUser(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }
}
