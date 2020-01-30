package com.retail.repository;

import com.retail.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<User, String> {

  /**
   * @param username
   * @return
   */
  public User findByUsername(String username);
}
