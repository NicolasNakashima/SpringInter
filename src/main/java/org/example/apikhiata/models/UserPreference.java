package org.example.apikhiata.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "user_preference")
public class UserPreference {

    @Id
    @Column(name = "pfk_user_id")
    @Schema(description = "ID do usuário", example = "1")
    private int pfkUserId;

    @Id
    @Schema(description = "valor da preferência", example = "crochê")
    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pfk_user_id", insertable = false, updatable = false)
    @JsonBackReference
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
        this.pfkUserId = user.getId();
    }
}

