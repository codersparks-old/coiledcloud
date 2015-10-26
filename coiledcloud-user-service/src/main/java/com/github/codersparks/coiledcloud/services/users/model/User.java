package com.github.codersparks.coiledcloud.services.users.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by codersparks on 12/10/2015.
 */
@XmlRootElement(name = "user")
public class User {

    @Id
    private Long id;

    @NotNull(message = "Username cannot be null")
    @Size(min = 5, message = "Username must be at least 5 characters")
    @Indexed(unique = true)
    private String username;

    @NotNull(message = "First name cannot be null")
    @Size(min = 2, message = "Firstname must be at least 2 characters")
    private String firstName;

    @Size(min = 2, message = "Lastname must be at least 2 characters")
    @NotNull(message = "Last name cannot be null")
    private String lastName;


    @NotNull(message = "Password cannot be empty")
    private String password;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }
}
