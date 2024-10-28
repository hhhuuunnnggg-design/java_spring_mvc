package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.entity.User;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String handleHello() {
        return "Hello from service";
    }

    public User handleSaveUser(User user) {
        return this.userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public List<User> getAllUsersByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
}
