package com.ftn.plagiator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftn.plagiator.dto.LoginDTO;
import com.ftn.plagiator.dto.RegistrationDTO;
import com.ftn.plagiator.dto.UserDTO;
import com.ftn.plagiator.model.Role;
import com.ftn.plagiator.model.User;
import com.ftn.plagiator.service.EmailService;
import com.ftn.plagiator.service.UserService;
import com.ftn.plagiator.service.UserService2;
import com.ftn.plagiator.util.ObjectMapperUtil;

@Controller
public class UserController {

	@Autowired
	UserService2 userService2;
	
	@Autowired
	UserService userService;
	
	@Autowired
	EmailService emailService;
	
//	@Autowired
//	ModelMapper modelMapper;
	
	@Autowired
	ObjectMapperUtil objectMapper;
	
	public UserController() {
//		modelMapper.getConfiguration().setAmbiguityIgnored(true);
//	    modelMapper.addMappings(companyPropertyMap);
	    
	}
	
//    PropertyMap<User, UserDTO> companyPropertyMap = new PropertyMap<User, UserDTO>() {
//        @Override
//        protected void configure() {
//            map().setPhoneNumber("123");
//        }
//    };
	
    @RequestMapping(value = "api/elastics", method = RequestMethod.GET)
    public ResponseEntity<Void> getRADI() {

    	userService2.saveUser();
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping(value = "api/login", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {

		User user = userService.findByEmail(loginDTO.getEmail());
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if (!user.isActive()) {
			// ne moze da se uloguje posto nije aktiviran mail
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		try {
			String jwt = userService.signin(loginDTO.getEmail(), loginDTO.getPassword());
			ObjectMapper mapper = new ObjectMapper();
			return new ResponseEntity<>(mapper.writeValueAsString(jwt), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}
	}

	@RequestMapping(value = "api/signup", method = RequestMethod.POST)
	public ResponseEntity<Void> signup(@RequestBody RegistrationDTO registrationDTO) {

		if (!registrationDTO.getRepeatedPassword().equals(registrationDTO.getPassword())) {
			return new ResponseEntity<>(HttpStatus.LOCKED);
		}
		
		User tempUser = userService.findByEmail(registrationDTO.getEmail());
		if(tempUser != null) {
			//mora biti jedinstveni mail za korisnika
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		User user = new User();
		user.setEmail(registrationDTO.getEmail());
		user.setPassword(registrationDTO.getPassword());
		user.setPhoneNumber(registrationDTO.getPhoneNumber());
		user.setName(registrationDTO.getName());
		user.setLastName(registrationDTO.getLastName());
		Role registeredUser = new Role();
		registeredUser.setId(1L);
		user.setRole(registeredUser);
		user.setActive(false);
		User retValue = userService.signup(user);

		try {
			emailService.sendNotificaitionAsync(retValue);
		} catch (MailException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "api/email/{email}", method = RequestMethod.GET)
	public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
		
		return new ResponseEntity<>(new UserDTO(userService.findByEmail(email)), HttpStatus.OK);
	}
	
	@GetMapping(path = "api/registration/activate/{userId}", produces = "application/json")
	public ResponseEntity<Void> activateUser(@PathVariable("userId") Long userId) {

		User user = userService.findOne(userId);
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		user.setActive(true);
		userService.save(user);
	
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(path = "api/proba/test/{userId}", produces = "application/json")
	public ResponseEntity<List<UserDTO>> tryMapper(@PathVariable("userId") Long userId, Pageable page) {
//		
//		if(userId != 0) {
//			User user = userService.findOne(userId);
//			UserDTO userDTO = modelMapper.map(user, UserDTO.class);
//		}
//		else {
			Page<User> users = userService.findAll(page);
			//List<User> users2 = new ArrayList<User>();
//			
//			users.forEach(item -> {
//				users2.add(item);
//			});
			//List<UserDTO> usersDTO = modelMapper.map(users2, new TypeToken<List<UserDTO>>() {}.getType());
			List<UserDTO> usersDTO = objectMapper.mapAll(users, UserDTO.class);
			
		//}

		return new ResponseEntity<>(usersDTO, HttpStatus.OK);
	}
	
}
