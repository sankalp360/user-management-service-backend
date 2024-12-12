package org.publicis.users.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.publicis.users.exception.UserNotFoundException;
import org.publicis.users.model.UserEntity;
import org.publicis.users.model.UsersWrapper;
import org.publicis.users.repository.UserRepository;
import org.publicis.users.util.JsonUtils;
import org.publicis.users.util.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    private String externalApiUrl = "https://dummyjson.com/users";

    public UserService(UserRepository userRepository, RestClient restClient) {
        this.userRepository = userRepository;
        this.restClient = restClient;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public void loadUsersFromExternalAPI() {
        logger.info("Fetching users from external API: {}", externalApiUrl);
        try {
            String jsonData = restClient.fetchData(externalApiUrl);
            UsersWrapper usersWrapper = JsonUtils.parseJsonToObject(jsonData, UsersWrapper.class, objectMapper);
            List<UserEntity> userEntityList = usersWrapper.getUsers();
            userRepository.saveAll(userEntityList);
            logger.info("Successfully loaded {} users into the database", userEntityList.size());
        } catch (JsonProcessingException e) {
            logger.error("Error parsing JSON data: {}", e.getMessage());
            throw new RuntimeException("Failed to parse external API response");
        } catch (Exception e) {
            logger.error("Error loading users: {}", e.getMessage());
            throw new RuntimeException("Failed to load users from external API");
        }
    }

    public List<UserEntity> getAllUsers() {
        logger.info("Fetching all users from the database");
        return userRepository.findAll();
    }


    public List<UserEntity> getUsersByRole(String role) {
        List<UserEntity> users = userRepository.findByRole(role);
        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found with role: " + role);
        }
        return users;
    }

    public List<UserEntity> getUsersSortedByAgeAsc() {
        logger.info("Fetching users sorted by age (ascending)");
        return userRepository.findByOrderByAgeAsc();
    }

    public List<UserEntity> getUsersSortedByAgeDesc() {
        logger.info("Fetching users sorted by age (descending)");
        return userRepository.findByOrderByAgeDesc();
    }

    public UserEntity getUserByIdOrSsn(Long id, String ssn) {
        return userRepository.findByIdOrSsn(id, ssn)
                .orElseThrow(() -> new UserNotFoundException("User not found for ID: " + id + " or SSN: " + ssn));
    }
}
