package com.vipdsilva.app.ws.controller;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.vipdsilva.app.ws.config.security.TokenService;
import com.vipdsilva.app.ws.model.request.UserRequestModel;
import com.vipdsilva.app.ws.model.response.UserDtoResponseModel;
import com.vipdsilva.app.ws.model.response.WarningDtoResponseModel;
import com.vipdsilva.app.ws.repository.UserRepository;
import com.vipdsilva.app.ws.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UsersController {

	// Injeção do Repository
	@Autowired
	private UserRepository userRepository;

	@Autowired
	UserService userService;

	@Autowired
	TokenService tokenService;

	@PostMapping
	public ResponseEntity<UserDtoResponseModel> addUser(@RequestBody UserRequestModel userInfo) {

		UserDtoResponseModel returnValue = userService.createUser(userInfo, userRepository);

		return new ResponseEntity<UserDtoResponseModel>(returnValue, HttpStatus.CREATED);
	}

	@DeleteMapping
	@Transactional
	public ResponseEntity<WarningDtoResponseModel> delete(HttpServletRequest request) {

		String token = tokenService.getToken(request); 
		Long userId = tokenService.getUserId(token);

		userService.deleteUser(userId, userRepository);

		WarningDtoResponseModel responseMsg = 
		new WarningDtoResponseModel("userId " + userId + " deletado com sucesso");

		return new ResponseEntity<WarningDtoResponseModel>(responseMsg, HttpStatus.OK);

	}

}
