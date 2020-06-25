package com.ftn.plagiator.service;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.plagiator.elasticsearch.model.UserElastics;
import com.ftn.plagiator.elasticsearch.repository.UserElasticsRepository;
import com.ftn.plagiator.model.User;
import com.ftn.plagiator.repository.UserRepository;

@Service
public class UserService2 {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserElasticsRepository userElasticsRepository;
	
//	@Autowired
//	PaperElasticRepository paperElasticRepository;
	
	@PostConstruct
	public void saveUser() {
		User u = new User();
		u.setId(8L);
		u.setEmail("email9");
		u.setName("ime9");
		u.setLastName("prezime9");
		
		Optional<User> found = userRepository.findById(1L);
		
		if(!found.isPresent()) {
			userRepository.save(u);
		}
		

		UserElastics elasticUser = new UserElastics();
		elasticUser.setId(11L);
		elasticUser.setEmail(u.getEmail());
		elasticUser.setLastName(u.getLastName());
		elasticUser.setName(u.getName());
		elasticUser.setText("this is some text. Try to find something, something like table, char, mobile");
		
		
		userElasticsRepository.save(elasticUser);
		
		UserElastics elasticUser2 = new UserElastics();
		elasticUser2.setId(12L);
		elasticUser2.setEmail(u.getEmail());
		elasticUser2.setLastName(u.getLastName());
		elasticUser2.setName(u.getName());
		elasticUser2.setText("Stefan said that Nevena is stupid, and she does not want to come home so she does not love him!");
		//elasticUser2.setAbstractForPaper("стефан проба nevena da li radi latinica");
		
		userElasticsRepository.save(elasticUser2);
		
//		PaperElastic pe = new PaperElastic();
//		pe.setId(1L);
//		pe.setTitle("стефан проба nevena da li radi latinica");
//		
//		paperElasticRepository.save(pe);
	}
	
}
