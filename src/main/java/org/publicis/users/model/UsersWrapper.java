package org.publicis.users.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersWrapper {

    // A list of UserDTO objects
    private List<UserEntity> userEntities;

    // Getter method to access the list of userEntities
    public List<UserEntity> getUsers() {
        return userEntities;
    }

    // Setter method to set the list of userEntities
    public void setUsers(List<UserEntity> userEntities) {
        this.userEntities = userEntities;
    }
}
