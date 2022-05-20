package com.vipdsilva.app.ws.service;

import java.util.List;

import com.vipdsilva.app.ws.model.request.UserRequestModel;
import com.vipdsilva.app.ws.model.response.UserDtoResponseModel;
import com.vipdsilva.app.ws.repository.ProfileRepository;
import com.vipdsilva.app.ws.repository.UserRepository;

public interface UserService {

	UserDtoResponseModel createUser(UserRequestModel userInfo,  UserRepository userRepository);
	void deleteUser(Long userId, UserRepository userRepository);
    UserDtoResponseModel updateProfile(Long userId, List<String> profiles,
	 UserRepository userRepository, ProfileRepository profileRepository);
}
