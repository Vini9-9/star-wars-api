package com.vipdsilva.app.ws.service;

import java.util.List;

import com.vipdsilva.app.ws.entities.User;
import com.vipdsilva.app.ws.model.request.UpdateUserRequestModel;
import com.vipdsilva.app.ws.model.request.UserRequestModel;
import com.vipdsilva.app.ws.model.response.UserDtoResponseModel;
import com.vipdsilva.app.ws.repository.ProfileRepository;
import com.vipdsilva.app.ws.repository.UserRepository;

public interface UserService {

	UserDtoResponseModel createUser(UserRequestModel userInfo, 
	 UserRepository userRepository, ProfileRepository profileRepository);

	void deleteUser(Long userId, UserRepository userRepository);
	
    UserDtoResponseModel updateUser(UpdateUserRequestModel body,
	 UserRepository userRepository, ProfileRepository profileRepository);

    List<User> getAll(UserRepository userRepository);
}
