package com.retail.service;

import com.retail.entity.Product;
import com.retail.entity.Role;
import com.retail.entity.User;
import com.retail.repository.ProductRepository;
import com.retail.repository.RoleRepository;
import com.retail.repository.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class InitDbSeederService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepostory;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Default constructor.
	 */
	public InitDbSeederService() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Populate data after initialize the application.
	 */
	@PostConstruct
	public void init() {
		addProduct();
		addUserAccount();
	}

	/**
	 * Add dummy products.
	 */
	private void addProduct() {
		if (productRepository != null) {
			Map<String, String> currency1 = new HashMap<>();
			currency1.put("value", "50");
			currency1.put("currency_code", "USD");
			Product product1 = new Product("13860428", "", currency1);

			Map<String, String> currency2 = new HashMap<>();
			currency2.put("value", "100.50");
			currency2.put("currency_code", "USD");
			Product product2 = new Product("16483589", "", currency2);

			Map<String, String> currency3 = new HashMap<>();
			currency3.put("value", "55");
			currency3.put("currency_code", "USD");
			Product product3 = new Product("16696652", "", currency3);

			Map<String, String> currency4 = new HashMap<>();
			currency4.put("value", "105.50");
			currency4.put("currency_code", "USD");
			Product product4 = new Product("15643793", "", currency4);

			// delete previous data
			this.productRepository.deleteAll();

			// Add product List in db.
			List<Product> products = Arrays.asList(product1, product2, product3, product4);
			this.productRepository.saveAll(products);
		}
	}
	
	/**
	 * To add the user accounts with Roles.
	 */
	private void addUserAccount() {
		if(userRepository != null) {
			// delete previous data
			this.roleRepostory.deleteAll();
			
			Role roleUser = new Role("1","USER");
			roleRepostory.save(roleUser);
			
			Role roleAdmin = new Role("2","ADMIN");
			roleRepostory.save(roleAdmin);
			
			
			//*********Add user and admin role for rohit*****************************
			List<Role> roles2 = new ArrayList<>();
			roles2.add(roleUser);
			roles2.add(roleAdmin);
			
			List<User> accounts = new ArrayList<>();
			
			User account1 = new User("1","admin",passwordEncoder.encode("admin"),roles2);
			accounts.add(account1);
			//*******************************************
			
			//*********Add only user role for Monika and Vivaan**********************************
			List<Role> roles1 = new ArrayList<>();
			roles1.add(roleUser);
			User account2 = new User("2","normaluser",passwordEncoder.encode("normalUser"),roles1);
			accounts.add(account2);
			
			User account3 = new User("3","dbuser",passwordEncoder.encode("dbuser"),roles1);
			accounts.add(account3);
			//*******************************************
			
			// delete previous data
			this.userRepository.deleteAll();
			
			//Save 
			this.userRepository.saveAll(accounts);
		}
	}
}
