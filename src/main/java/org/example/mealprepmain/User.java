package org.example.mealprepmain;

import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String birthday;
    private List<String> preferences; //food preferences

    public User(String firstName, String lastName, String email, String birthday, List<String> preferences){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.preferences = preferences;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getBirthday() {
        return birthday;
    }
    public List<String> getPreferences() {
        return preferences;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public void setPreferences(List<String> preferences) {
        this.preferences = preferences;
    }

}
