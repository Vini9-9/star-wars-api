package com.vipdsilva.app.ws.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.vipdsilva.app.ws.entities.Profile;
import com.vipdsilva.app.ws.entities.User;
import com.vipdsilva.app.ws.exceptions.AlreadyExistsException;
import com.vipdsilva.app.ws.exceptions.NotFoundException;
import com.vipdsilva.app.ws.model.request.UpdateUserRequestModel;
import com.vipdsilva.app.ws.model.request.UserRequestModel;
import com.vipdsilva.app.ws.model.response.UserDtoResponseModel;
import com.vipdsilva.app.ws.repository.ProfileRepository;
import com.vipdsilva.app.ws.repository.UserRepository;
import com.vipdsilva.app.ws.service.UserService;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserDtoResponseModel createUser(UserRequestModel userInfo, 
    UserRepository userRepository, ProfileRepository profileRepository) {
        String userEmail = userInfo.getEmail();

        if(!hasEmail(userEmail, userRepository)){
            
            Profile profileDefault = profileRepository
            .findByName("ROLE_USUARIO").get();
            
            User user = new User(userInfo, profileDefault);
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

    @Override
    public List<User> getAll(UserRepository userRepository) {
        return userRepository.findAll();
    }

    @Override
    public UserDtoResponseModel updateUser(UpdateUserRequestModel body, 
    UserRepository userRepository, ProfileRepository profileRepository) {
                Optional<User> optUserUpdated = userRepository.findById(body.getId());

                if(optUserUpdated.isPresent()){
                    User userUpdated = optUserUpdated.get();
                    String nameReq = body.getName();
                    String emailReq = body.getEmail();
                    List<String> profilesName = body.getProfiles();
        
                    if(profilesName != null){
                        
                        List<Profile> validProfiles = new ArrayList<>(); 
        
                        for (String profileName : profilesName) {
        
                            Optional<Profile> profile = profileRepository
                            .findByName("ROLE_" + profileName.toUpperCase().trim());
        
                            if(profile.isPresent()){
                                validProfiles.add(profile.get());
                            } else {
                                throw new NotFoundException("Perfil com nome " + profileName + " não localizado");
                            }
                        }
        
                        if(!validProfiles.isEmpty()){
                            userUpdated.clearProfiles();
                            userUpdated.setProfiles(validProfiles);
                        }
                    }
                    
                    if (nameReq != null && !nameReq.isBlank())
                    userUpdated.setName(nameReq);
                    if (emailReq != null && !emailReq.isBlank())
                    userUpdated.setEmail(emailReq);
                    
                    return userUpdated.toResponseDto();
                }
            
                throw new NotFoundException("Usuário com id " + body.getId() + " não localizado");
    }
    
}
