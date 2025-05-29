package org.datosii.tpidatosii.service;

import org.datosii.tpidatosii.dto.UserCreateDto;
import org.datosii.tpidatosii.model.User;
import org.datosii.tpidatosii.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserCreateDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User(userDto.getUsername(), userDto.getEmail());
        user.setProfilePicture(userDto.getProfilePicture());
        user.setBio(userDto.getBio());

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void followUser(Long followerId, Long followedId) {
        if (followerId.equals(followedId)) {
            throw new RuntimeException("User cannot follow themselves");
        }
        userRepository.followUser(followerId, followedId);
    }

    public void unfollowUser(Long followerId, Long followedId) {
        userRepository.unfollowUser(followerId, followedId);
    }

    public List<User> getFollowing(Long userId) {
        return userRepository.findFollowing(userId);
    }

    public List<User> getFollowers(Long userId) {
        return userRepository.findFollowers(userId);
    }

    public List<User> getRecommendedUsersByCommonLikes(Long userId) {
        return userRepository.findUsersByCommonLikes(userId);
    }
}