package com.dpvn.crmcrudservice.user;

import com.dpvn.crmcrudservice.domain.entity.*;
import com.dpvn.crmcrudservice.repository.UserCustomRepository;
import com.dpvn.crmcrudservice.repository.UserRepository;
import com.dpvn.shared.domain.constant.Globals;
import com.dpvn.shared.exception.BadRequestException;
import com.dpvn.shared.service.AbstractCrudService;
import com.dpvn.shared.util.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService extends AbstractCrudService<User> {
  private final UserCustomRepository userCustomRepository;
  private final RoleService roleService;
  private final DepartmentService departmentService;

  public UserService(
      UserRepository repository,
      UserCustomRepository userCustomRepository,
      UserRepository userRepository,
      RoleService roleService,
      DepartmentService departmentService) {
    super(repository);
    this.userCustomRepository = userCustomRepository;
    this.roleService = roleService;
    this.departmentService = departmentService;
  }

  @Transactional
  @Override
  public void sync(List<User> entities) {
    List<User> users = new ArrayList<>();
    entities.forEach(
        entity -> {
          User dbUser = findByUsername(entity.getUsername());
          if (dbUser == null) {
            entity.setPassword("123456");
            entity.setActive(false);
            users.add(entity);
          } else {
            ObjectUtil.assign(dbUser, entity, List.of("password", "status", "role", "department"));
            users.add(dbUser);
          }
        });
    saveAll(users);
  }

  public List<User> findUsersByIds(List<Long> ids) {
    return ((UserRepository) repository).findByIdIn(ids);
  }

  public User findByUsername(String username) {
    return ((UserRepository) repository).findByUsername(username);
  }

  public void changePassword(String username, String password) {
    User user = findByUsername(username);
    if (user == null) {
      throw new BadRequestException("User not found");
    }
    user.setPassword(password);
    user.setStatus("1");
    user.setActive(Boolean.TRUE);
    user.setDeleted(Boolean.FALSE);
    save(user);
  }

  public List<User> findUsersByOptions(
      Long idf,
      String username,
      String fullName,
      String email,
      String mobilePhone,
      String description,
      String address) {
    return ((UserRepository) repository)
        .findUsersByOptions(idf, username, fullName, email, mobilePhone, description, address);
  }

  public Page<User> searchUsers(
      String filterText,
      Boolean active,
      List<Long> departments,
      List<Long> roles,
      Integer page,
      Integer pageSize) {
    Pageable pageable =
        (page == null || pageSize == null)
            ? PageRequest.of(0, Integer.MAX_VALUE)
            : PageRequest.of(page, pageSize);
    return userCustomRepository.searchUsers(filterText, active, departments, roles, pageable);
  }

  public List<User> findByIds(List<Long> userIds) {
    return ((UserRepository) repository).findByIdIn(userIds);
  }

  @Override
  @Transactional
  public User create(User entity) {
    User dbUser = findByIdOrIdf(entity.getIdf(), entity.getId()).orElse(null);
    if (dbUser != null) {
      throw new BadRequestException("Entity already exists.");
    }

    if (StringUtil.isEmpty(entity.getPassword())) {
      entity.setPassword("123456");
      entity.setActive(Boolean.TRUE);
      entity.setDeleted(Boolean.FALSE);
      entity.setStatus(null);
    }

    save(entity);
    return entity;
  }

  @Override
  @Transactional
  public User update(Long id, FastMap data) {
    User dbUser = findById(id).orElseThrow(() -> new BadRequestException("User not found"));
    Long roleId = data.getLong("roleId");
    if (dbUser.getRole() != null && !dbUser.getRole().getId().equals(roleId)) {
      Role role =
          roleService.findById(roleId).orElseThrow(() -> new BadRequestException("Role not found"));
      dbUser.setRole(role);
    }

    Long departmentId = data.getLong("departmentId");
    if (dbUser.getDepartment() != null && !dbUser.getDepartment().getId().equals(departmentId)) {
      Department department =
          departmentService
              .findById(departmentId)
              .orElseThrow(() -> new BadRequestException("Department not found"));
      dbUser.setDepartment(department);
    }

    List<UserProperty> userProperties = data.getListClass("properties", UserProperty.class);
    Instant now = DateUtil.now();
    //    List<UserProperty> userProperties =
    //        userPropertyDtos.stream().map(dto-> {
    //          UserProperty entity = dto.toEntity();
    //          entity.setActive(Boolean.TRUE);
    //          entity.setDeleted(Boolean.FALSE);
    //          entity.setCreatedDate(now);
    //          entity.setModifiedDate(now);
    //          entity.setFromDate(now);
    //          if ("MEMBER".equals(entity.getCode()) && "JUDAS".equals(entity.getStatus())) {
    //            entity.setFromDate(now.plus(Globals.Customer.LIFE_TIME_TREASURE_IN_DAYS,
    // ChronoUnit.DAYS));
    //          }
    //          return entity;
    //        }).toList();
    userProperties.forEach(
        up -> {
          up.setUser(dbUser);
          up.setActive(Boolean.TRUE);
          up.setDeleted(Boolean.FALSE);
          up.setCreatedDate(now);
          up.setModifiedDate(now);
          up.setFromDate(now);
          if ("MEMBER".equals(up.getCode()) && "JUDAS".equals(up.getStatus())) {
            up.setToDate(now.plus(Globals.Customer.LIFE_TIME_TREASURE_IN_DAYS, ChronoUnit.DAYS));
          }
        });
    dbUser.getProperties().clear();
    dbUser.getProperties().addAll(userProperties);

    ObjectUtil.assign(dbUser, data, List.of("roleId", "departmentId", "properties"));
    save(dbUser);

    return dbUser;
  }
}
