package com.tms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tms.annotation.CustomAge;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@Scope("prototype")
@Component
@Entity(name = "users")
public class User {
    @Id
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_seq_gen")
    private Long id;

    @NotNull(message = "Firstname cannot be null.")
    @Size(min = 2, max = 20, message = "Firstname must be between 2 and 20 characters.")
    private String firstname;

    @NotNull(message = "Second name cannot be null.")
    @Size(min = 2, max = 20, message = "Second name must be between 2 and 20 characters.")
    @Column(name = "second_name")
    private String secondName;

    @CustomAge
    private Integer age;

    @NotNull(message = "Email cannot be null.")
    @Email(message = "Invalid email format.")
    private String email;

    private String sex;

    @Pattern(regexp = "[0-9]{12}", message = "Telephone number must be exactly 12 digits.")
    @Column(name = "telephone_number")
    private String telephoneNumber;

    @JsonIgnore
    @Column(name = "created", updatable = false)
    private Timestamp created;

    @JsonIgnore
    @Column(name = "updated")
    private Timestamp updated;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
    
    @PrePersist
    protected void onCreate() {
        created = new Timestamp(System.currentTimeMillis());
        updated = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Timestamp(System.currentTimeMillis());
    }
}
