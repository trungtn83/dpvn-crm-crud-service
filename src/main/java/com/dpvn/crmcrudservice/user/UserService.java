package com.dpvn.crmcrudservice.user;

import com.dpvn.crmcrudservice.domain.dto.UserPropertyDto;
import com.dpvn.crmcrudservice.domain.entity.*;
import com.dpvn.crmcrudservice.repository.UserCustomRepository;
import com.dpvn.crmcrudservice.repository.UserMemberRepository;
import com.dpvn.crmcrudservice.repository.UserRepository;
import com.dpvn.shared.exception.BadRequestException;
import com.dpvn.shared.service.AbstractCrudService;
import com.dpvn.shared.util.FastMap;
import com.dpvn.shared.util.ObjectUtil;
import com.dpvn.shared.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
  private final UserMemberRepository userMemberRepository;

  public UserService(
      UserRepository repository,
      UserCustomRepository userCustomRepository,
      UserRepository userRepository,
      RoleService roleService,
      DepartmentService departmentService,
      UserMemberRepository userMemberRepository) {
    super(repository);
    this.userCustomRepository = userCustomRepository;
    this.roleService = roleService;
    this.departmentService = departmentService;
    this.userMemberRepository = userMemberRepository;
  }

  @Transactional
  @Override
  public void sync(List<User> entities) {
    List<User> users = new ArrayList<>();
    entities.forEach(
        entity -> {
          User dbUser = findByUsername(entity.getUsername()).orElse(null);
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

    List<UserPropertyDto> userPropertyDtos = data.getListClass("properties", UserPropertyDto.class);
    List<UserProperty> userProperties =
        userPropertyDtos.stream().map(UserPropertyDto::toEntity).toList();
    userProperties.forEach(up -> up.setUser(dbUser));
    dbUser.getProperties().clear();
    dbUser.getProperties().addAll(userProperties);

    ObjectUtil.assign(dbUser, data, List.of("roleId", "departmentId", "properties"));
    save(dbUser);

    return dbUser;
  }

  public void addMember(Long leaderId, Long memberId) {
    UserMember existed = userMemberRepository.findByLeaderIdAndMemberId(memberId, leaderId);
    if (existed != null) {
      throw new BadRequestException(
          String.format(
              "Relationship leader-member [%d - %s] already exists.", leaderId, memberId));
    }

    List<User> users = findUsersByIds(List.of(leaderId, memberId));
    User leader =
        users.stream().filter(user -> user.getId().equals(leaderId)).findFirst().orElseThrow();
    User member =
        users.stream().filter(user -> user.getId().equals(memberId)).findFirst().orElseThrow();

    leader.getMembers().add(member);
    member.getLeaders().add(leader);
    save(leader);
    save(member);
  }

  public void removeMember(Long leaderId, Long memberId) {
    List<User> users = findUsersByIds(List.of(leaderId, memberId));
    User leader =
        users.stream().filter(user -> user.getId().equals(leaderId)).findFirst().orElseThrow();
    User member =
        users.stream().filter(user -> user.getId().equals(memberId)).findFirst().orElseThrow();

    leader.getMembers().remove(member);
    member.getLeaders().remove(leader);
    save(leader);
    save(member);
  }
}
