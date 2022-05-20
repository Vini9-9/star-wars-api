package com.vipdsilva.app.ws.service.impl;

import java.util.Optional;

import com.vipdsilva.app.ws.entities.User;
import com.vipdsilva.app.ws.exceptions.AlreadyExistsException;
import com.vipdsilva.app.ws.exceptions.NotFoundException;
import com.vipdsilva.app.ws.model.request.UserRequestModel;
import com.vipdsilva.app.ws.model.response.UserDtoResponseModel;
import com.vipdsilva.app.ws.repository.UserRepository;
import com.vipdsilva.app.ws.service.UserService;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserDtoResponseModel createUser(UserRequestModel userInfo, UserRepository userRepository) {
        String userEmail = userInfo.getEmail();

        if(!hasEmail(userEmail, userRepository)){
            User user = new User(userInfo);
            userRepository.save(user);
            UserDtoResponseModel response = user.toResponseDto();
            return response;
        };
    
        throw new AlreadyExistsException("e-mail já cadastrado");
    }

    @Override
    public void deleteUser(Long userId, UserRepository userRepository) {
        Optional<User> existUser = userRepository.findById(userId);

        if(existUser.isPresent()){
            userRepository.deleteById(userId);
        } else {
            throw new NotFoundException("userID: " + userId + " não localizado");
        }
        
    }

    public boolean hasEmail (String userEmail, UserRepository userRepository) {
        return userRepository.findByEmail(userEmail).isPresent();
    }
    
}
