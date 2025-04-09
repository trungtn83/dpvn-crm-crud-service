package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.User;
import com.dpvn.shared.repository.AbstractRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends AbstractRepository<User> {
  List<User> findByIdIn(List<Long> ids);

  User findByUsername(String username);

  String FIND_USERS_BY_OPTIONS =
      """
      SELECT u FROM User u
      WHERE (:username IS NULL OR u.username = :username)
          AND (:idf IS NULL OR u.idf  = :idf)
          AND (:fullName IS NULL OR u.fullName  = :fullName)
          AND (:email IS NULL OR u.email = :email)
          AND (:mobilePhone IS NULL OR u.mobilePhone = :mobilePhone)
          AND (:description IS NULL OR u.description = :description)
          AND (:address IS NULL OR u.addressDetail = :address)
      """;

  @Query(FIND_USERS_BY_OPTIONS)
  List<User> findUsersByOptions(
      @Param("idf") Long idf,
      @Param("username") String username,
      @Param("fullName") String fullName,
      @Param("email") String email,
      @Param("mobilePhone") String mobilePhone,
      @Param("description") String description,
      @Param("address") String address);
}
