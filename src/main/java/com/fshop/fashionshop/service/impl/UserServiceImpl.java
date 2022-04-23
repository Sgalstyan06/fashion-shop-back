package com.fshop.fashionshop.service.impl;

import com.fshop.fashionshop.model.User;
import com.fshop.fashionshop.repository.UserRepository;
import com.fshop.fashionshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    /***
     *
     * @param user from client which will be added to the db
     * @return returns created user info
     */
    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    /***
     *
     * @param id from client to find user
     * @return returns founded user info or else throws ResponseStatusException(HttpStatus.BAD_REQUEST)
     */
    @Override
    public User getById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "user with id:" + id + " not founded"));
    }

    @Override
    public User getByIdForSingUp(String id) {
        return userRepository.getById(id);
    }

    @Override
    public boolean isExists(String id) {
        return userRepository.findById(id).isPresent();
    }



    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(String id) {
        userRepository.deleteById(id);
    }
}
