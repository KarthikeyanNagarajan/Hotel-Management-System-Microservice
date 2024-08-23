package com.karthik.hotelmanagement.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.karthik.hotelmanagement.entity.Role;
import com.karthik.hotelmanagement.entity.User;
import com.karthik.hotelmanagement.feignclient.UserAuthFeignClient;
import com.karthik.hotelmanagement.model.UserRegisterDto;
import com.karthik.hotelmanagement.repository.RoleRepository;
import com.karthik.hotelmanagement.repository.UserRepository;

@Service
public class UserInfoService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserAuthFeignClient userAuthFeignClient;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<User> getUsers() {

		List<User> all = userRepository.findAll();
		return all;
	}

	public List<Role> getRoles() {

		return roleRepository.findAll();
	}

	public User getUserById(int id) {
		return userRepository.findById(id).orElse(null);
	}

	public void deleteUser(int id) {
		userRepository.deleteById(id);
	}

	public User updateUser(int id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByName(username).orElse(null);
		if (user != null)
			return new UserInfoDetails(user);

		return null;
	}

	public void register(UserRegisterDto userDto) {

		Role role = roleRepository.findById(1).orElse(null);
		User user = new User();
		user.setName(userDto.getName());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setEnabled(true);
		
		Set<Role> set = new HashSet<>();		
		set.add(role);		
		user.setRoles(set);
		
		User saved = userRepository.save(user);
		userDto.setUserid(saved.getId());
		userAuthFeignClient.saveUser(userDto);
	}
}
