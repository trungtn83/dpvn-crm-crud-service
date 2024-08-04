package com.dpvn.crmcrudservice.user;

import com.dpvn.crmcrudservice.AbstractService;
import com.dpvn.crmcrudservice.domain.Status;
import com.dpvn.crmcrudservice.domain.entity.User;
import com.dpvn.crmcrudservice.repository.UserRepository;
import com.dpvn.shared.exception.BadRequestException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<User> {

  public UserService(UserRepository repository) {
    super(repository);
  }

  @Override
  public void sync(List<User> entities) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public List<User> findByIds(List<Long> ids) {
    return ((UserRepository) repository).findByIdIn(ids);
  }

  public void saveAll(List<User> users) {
    repository.saveAll(users);
  }

  public Optional<User> findByUsername(String username) {
    return ((UserRepository) repository).findByUsername(username);
  }

  public void changePassword(String username, String password) {
    Optional<User> userOpt = findByUsername(username);
    if (userOpt.isEmpty()) {
      throw new BadRequestException("User not found");
    }
    User user = userOpt.get();
    user.setPassword(password);
    user.setStatus(Status.ACTIVE);
    repository.save(user);
  }
}
