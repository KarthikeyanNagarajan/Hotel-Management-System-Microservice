package com.karthik.hotelmanagement.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.karthik.hotelmanagement.entity.User;
import com.karthik.hotelmanagement.entity.Wallet;
import com.karthik.hotelmanagement.repository.UserRepository;
import com.karthik.hotelmanagement.repository.WalletRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WalletRepository walletRepository;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUserById(int id) {
		return userRepository.findById(id).orElse(null);
	}

	public User getUserByName(String name) {
		return userRepository.findByName(name).orElse(null);
	}

	public User saveUser(User user) {
		User userDto = userRepository.save(user);
		if (userDto != null) {
			Wallet wallet = createWallet(userDto);
			userDto.setWallet(wallet);
		}
		return userDto;
	}

	public Wallet getWalletByUserId(int id) {

		Wallet wallet = null;
		User user = getUserById(id);
		if (user != null)
			wallet = user.getWallet();

		return wallet;
	}

	public Integer getUserBalance(int id) {
		int balance = 0;
		Wallet wallet = getWalletByUserId(id);
		if (wallet != null)
			balance = wallet.getBalance();
		return balance;
	}

	public Wallet updateBalance(int id, int amount) {
		Wallet wallet = getWalletByUserId(id);
		if (wallet != null) {
			wallet.setBalance(amount);
			walletRepository.save(wallet);
		}
		return wallet;
	}

	public Wallet createWallet(User user) {
		Wallet wallet = new Wallet();
		wallet.setBalance(0);
		wallet.setUser(user);
		return walletRepository.save(wallet);
	}
}
