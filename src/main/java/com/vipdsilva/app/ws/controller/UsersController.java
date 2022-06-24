package com.vipdsilva.app.ws.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.vipdsilva.app.ws.config.security.TokenService;
import com.vipdsilva.app.ws.entities.User;
import com.vipdsilva.app.ws.model.request.DeleteUserRequestModel;
import com.vipdsilva.app.ws.model.request.UpdateUserRequestModel;
import com.vipdsilva.app.ws.model.request.UserRequestModel;
import com.vipdsilva.app.ws.model.response.UserDtoResponseModel;
import com.vipdsilva.app.ws.model.response.WarningDtoResponseModel;
import com.vipdsilva.app.ws.repository.ProfileRepository;
import com.vipdsilva.app.ws.repository.UserRepository;
import com.vipdsilva.app.ws.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	private ProfileRepository profileRepository;

	@Autowired
	UserService userService;

	@Autowired
	TokenService tokenService;

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping
	public ResponseEntity<UserDtoResponseModel> addUser(@RequestBody UserRequestModel userInfo) {

		UserDtoResponseModel returnValue = userService
		.createUser(userInfo, userRepository, profileRepository);

		return new ResponseEntity<UserDtoResponseModel>(returnValue, HttpStatus.CREATED);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {

		List<User> returnValue = userService
		.getAll(userRepository);

		return new ResponseEntity<List<User>>(returnValue, HttpStatus.OK);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping()
	@Transactional
	public ResponseEntity<UserDtoResponseModel> updateByAdmin(
	HttpServletRequest request, @RequestBody UpdateUserRequestModel body) {

		UserDtoResponseModel returnValue = userService
		.updateUser(body, userRepository, profileRepository);

		

		return new ResponseEntity<UserDtoResponseModel>(returnValue, HttpStatus.OK);

	}

	
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping
	@Transactional
	public ResponseEntity<WarningDtoResponseModel> delete(HttpServletRequest request) {

		String token = tokenService.getToken(request); 
		Long userId = tokenService.getUserId(token);

		userService.deleteUser(userId, userRepository);

		WarningDtoResponseModel responseMsg = 
		new WarningDtoResponseModel("Usuário", userId.intValue());

		return new ResponseEntity<WarningDtoResponseModel>(responseMsg, HttpStatus.OK);

	}
	
	@Profile(value = {"dev"})
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/admin")
	@Transactional
	public ResponseEntity<WarningDtoResponseModel> deleteDev(HttpServletRequest request, 
	@RequestBody DeleteUserRequestModel body) {

		Long userId = body.getId();

		userService.deleteUser(userId, userRepository);

		WarningDtoResponseModel responseMsg = 
		new WarningDtoResponseModel("Usuário", userId.intValue());

		return new ResponseEntity<WarningDtoResponseModel>(responseMsg, HttpStatus.OK);

	}

}
