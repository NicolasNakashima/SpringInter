package org.example.apikhiata.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "O nome não pode ser nulo")
    @Size(min = 1, max = 60, message = "O nome deve ter entre 1 e 60 caracteres")
    private String name;

    @NotNull(message = "O CPF não pode ser nulo")
    @CPF(message = "CPF inválido")
    private String cpf;

    @ManyToOne
    @JoinColumn(name = "gender_id", nullable = false)
    private Gender gender;

    @NotNull(message = "A idade não pode ser nula")
    private int age;

    @Column(name = "is_dressmaker")
    private boolean isDressmaker;

    @Column(name = "is_premium")
    private boolean isPremium;

    @NotNull(message = "O telefone não pode ser nulo")
    private int phone;

    @Size(max = 100, message = "A URL da imagem não pode exceder 100 caracteres")
    @Column(name = "image_url")
    private String imageURL;

    @NotNull(message = "A senha não pode ser nula")
    @Size(min = 8, max = 12, message = "A senha deve ter entre 8 e 12 caracteres")
    private String password;

    @NotNull(message = "O e-mail não pode ser nulo")
    @Email(message = "E-mail inválido")
    @Size(max = 100, message = "O e-mail não pode exceder 100 caracteres")
    private String email;

    @Size(max = 100, message = "A URL da foto de perfil não pode exceder 100 caracteres")
    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    // Construtor vazio
    public User() {
    }

    // Construtor completo
    public User(int id, String name, String cpf, Gender gender, int age, boolean isDressmaker, boolean isPremium,
                int phone, String imageURL, String password, String email, String profilePictureUrl) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.gender = gender;
        this.age = age;
        this.isDressmaker = isDressmaker;
        this.isPremium = isPremium;
        this.phone = phone;
        this.imageURL = imageURL;
        this.password = password;
        this.email = email;
        this.profilePictureUrl = profilePictureUrl;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getIsDressmaker() {
        return isDressmaker;
    }

    public void setIsDressmaker(boolean dressmaker) {
        isDressmaker = dressmaker;
    }

    public boolean getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(boolean premium) {
        isPremium = premium;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
}
