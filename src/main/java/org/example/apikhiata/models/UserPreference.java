package org.example.apikhiata.models;
import jakarta.persistence.*;

@Entity
@Table(name = "user_preference")
public class UserPreference {

    @Id
    @Column(name = "pfk_user_id")
    private int pfkUserId;

    @Id
    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pfk_user_id", insertable = false, updatable = false)  // Foreign key para User
    private User user;

    // Construtor padrão
    public UserPreference() {}

    public UserPreference(int pfkUserId, String value) {
        this.pfkUserId = pfkUserId;
        this.value = value;
    }

    // Getters e Setters
    public int getPfkUserId() {
        return pfkUserId;
    }

    public void setPfkUserId(int pfkUserId) {
        this.pfkUserId = pfkUserId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.pfkUserId = user.getId();  // Sincroniza o ID do usuário
    }
}

