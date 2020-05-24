package com.pet.tm.api.service;

import com.pet.tm.api.entity.UserEntity;
import com.pet.tm.api.exception.TaskNotFoundException;
import com.pet.tm.api.exception.UserNotFoundException;
import com.pet.tm.api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

  private static Logger LOG = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;

  public UserService(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserEntity createUser(@NonNull UserEntity user) {
    LOG.info("Creating User: \n \t\t{}", user);
    return userRepository.save(user);
  }

  public List<UserEntity> getAllUsers() {
    return userRepository.findAll();
  }

  public UserEntity getUserById(@NonNull Long id) {
	  return userRepository
			  .findById(id)
			  .orElseThrow(
					  () -> {
						  throw new UserNotFoundException("User with id: " + id + " does not exist");
					  });
  }
}
