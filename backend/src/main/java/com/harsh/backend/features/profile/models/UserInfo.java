package com.harsh.backend.features.profile.models;

import com.harsh.backend.features.auth.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(
        name = "user_info",
        indexes = {
            @Index
                    (
                            name = "idx_user_info_user_id",
                            columnList = "user_id"
                    )
        }
)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @Id
    private UUID userId;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String firstName;
    private String lastName;

    private String company;
    private String position;

    private String location;
    private String profilePicture;
    private String coverPicture;
    private String about;

    private Boolean isCompleted = false;


    @Column(length = 1000)
    private String bio;


    private String phoneNumber;
    private String address;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PostUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    private void updateIsCompleted() {
        this.isCompleted = lastName != null && company != null && position != null && location != null && profilePicture != null && coverPicture != null && about != null && bio != null && phoneNumber != null && address != null && dateOfBirth != null;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        updateIsCompleted();
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setUser(User user) {
        this.user = user;
        updateIsCompleted();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        updateIsCompleted();
    }

    public void setCompany(String company) {
        this.company = company;
        updateIsCompleted();
    }

    public void setPosition(String position) {
        this.position = position;
        updateIsCompleted();
    }

    public void setLocation(String location) {
        this.location = location;
        updateIsCompleted();
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        updateIsCompleted();
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
        updateIsCompleted();
    }

    public void setAbout(String about) {
        this.about = about;
        updateIsCompleted();
    }

    public void setBio(String bio) {
        this.bio = bio;
        updateIsCompleted();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        updateIsCompleted();
    }

    public void setAddress(String address) {
        this.address = address;
        updateIsCompleted();
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        updateIsCompleted();
    }
}
