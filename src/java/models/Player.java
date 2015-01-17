package models;

import java.io.Serializable;

public class Player implements Serializable {
    
    private String firstName;
    private String lastName;
    
    public Player()
    {
    }
    
    public Player(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
