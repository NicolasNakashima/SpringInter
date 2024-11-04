package org.example.apikhiata.models;

import java.util.List;

public class UserPreferencesDTO {
    private List<UserPreference> userPreferences;

    // Getters e Setters
    public List<UserPreference> getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(List<UserPreference> userPreferences) {
        this.userPreferences = userPreferences;
    }
}

