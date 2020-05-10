package com.netcracker.denisik.userDetails;

import com.netcracker.denisik.entity.Role;
import com.netcracker.denisik.entity.User;
import com.netcracker.denisik.repository.UserRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Slf4j
@Transactional
@Service
@Setter(onMethod_ = @Autowired)
public class UserServiceImpl{

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    @Transactional
    public void saveUser(String login, String password, Role role) throws Exception {
        if (!userRepository.existsByLogin(login)){
            User user = new User();
            user.setLogin(login);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(role);
            userRepository.save(user);
        } else {
            throw new Exception("user exists");
        }

    }

}
