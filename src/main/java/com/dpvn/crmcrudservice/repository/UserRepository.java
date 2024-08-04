package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findByIdIn(List<Long> ids);

  Optional<User> findByUsername(String username);
}
