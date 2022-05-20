package com.vipdsilva.app.ws.service;

import com.vipdsilva.app.ws.model.request.UserRequestModel;
import com.vipdsilva.app.ws.model.response.UserDtoResponseModel;
import com.vipdsilva.app.ws.repository.UserRepository;

public interface UserService {

	UserDtoResponseModel createUser(UserRequestModel userInfo,  UserRepository userRepository);
	void deleteUser(Long userId, UserRepository userRepository);
}
