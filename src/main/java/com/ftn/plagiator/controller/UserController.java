package com.ftn.plagiator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ftn.plagiator.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
    @RequestMapping(value = "api/elastics", method = RequestMethod.GET)
    public ResponseEntity<Void> getRADI() {

    	userService.saveUser();
    	return new ResponseEntity<>(HttpStatus.OK);
    }
	
}
