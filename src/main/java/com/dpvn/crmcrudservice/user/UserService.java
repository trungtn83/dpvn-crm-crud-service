package com.dpvn.crmcrudservice.user;

import com.dpvn.crmcrudservice.domain.entity.Department;
import com.dpvn.crmcrudservice.domain.entity.Role;
import com.dpvn.crmcrudservice.domain.entity.User;
import com.dpvn.crmcrudservice.repository.DepartmentRepository;
import com.dpvn.crmcrudservice.repository.RoleRepository;
import com.dpvn.crmcrudservice.repository.UserCustomRepository;
import com.dpvn.crmcrudservice.repository.UserRepository;
import com.dpvn.shared.exception.BadRequestException;
import com.dpvn.shared.service.AbstractService;
import com.dpvn.shared.util.FastMap;
import com.dpvn.shared.util.ObjectUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService extends AbstractService<User> {
  private final DepartmentRepository departmentRepository;
  private final RoleRepository roleRepository;
  private final UserCustomRepository userCustomRepository;

  public UserService(
      UserRepository repository,
      DepartmentRepository departmentRepository,
      RoleRepository roleRepository,
      UserCustomRepository userCustomRepository) {
    super(repository);
    this.departmentRepository = departmentRepository;
    this.roleRepository = roleRepository;
    this.userCustomRepository = userCustomRepository;
  }

  @Transactional
  @Override
  public void sync(List<User> entities) {
    Department department = departmentRepository.findByDepartmentName("SALE");
    Role role = roleRepository.findByRoleName("USER");

    List<User> users = new ArrayList<>();
    entities.forEach(
        entity -> {
          User dbUser = findByUsername(entity.getUsername()).orElse(null);
          if (dbUser == null) {
            entity.setPassword("123456");
            entity.setActive(false);
            entity.setDepartment(department);
            entity.setRole(role);
            users.add(entity);
          } else {
            ObjectUtil.assign(dbUser, entity, List.of("role", "department", "password", "status"));
            users.add(dbUser);
          }
        });
    saveAll(users);
  }

  public List<User> findUsersByIds(List<Long> ids) {
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
    user.setActive(Boolean.TRUE);
    repository.save(user);
  }

  public List<User> findUsersByOptions(
      String username,
      String fullName,
      String email,
      String mobilePhone,
      String description,
      String address) {
    return ((UserRepository) repository)
        .findUsersByOptions(username, fullName, email, mobilePhone, description, address);
  }

  public Page<FastMap> searchUsers(
      String filterText,
      List<String> departments,
      List<String> roles,
      Integer page,
      Integer pageSize) {
    Pageable pageable =
        (page == null || pageSize == null)
            ? PageRequest.of(0, Integer.MAX_VALUE)
            : PageRequest.of(page, pageSize);
    return userCustomRepository.searchUsers(filterText, departments, roles, pageable);
  }
}
