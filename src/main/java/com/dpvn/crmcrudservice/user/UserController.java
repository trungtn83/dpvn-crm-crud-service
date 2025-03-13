package com.dpvn.crmcrudservice.user;

import com.dpvn.crmcrudservice.domain.dto.UserDto;
import com.dpvn.crmcrudservice.domain.entity.Department;
import com.dpvn.crmcrudservice.domain.entity.Role;
import com.dpvn.crmcrudservice.domain.entity.User;
import com.dpvn.crmcrudservice.repository.CacheEntityService;
import com.dpvn.shared.controller.AbstractCrudController;
import com.dpvn.shared.domain.BeanMapper;
import com.dpvn.shared.util.FastMap;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends AbstractCrudController<User, UserDto> {

  private final CacheEntityService cacheEntityService;

  public UserController(UserService userService, CacheEntityService cacheEntityService) {
    super(userService);
    this.cacheEntityService = cacheEntityService;
  }

  @GetMapping("/username/{username}")
  public UserDto getCrmUserByUserName(@PathVariable String username) {
    Optional<User> user = ((UserService) service).findByUsername(username);
    return user.map(User::toDto).orElse(null);
  }

  @PostMapping("/change-password")
  public void changePassword(@RequestBody UserDto userDto) {
    ((UserService) service).changePassword(userDto.getUsername(), userDto.getPassword());
  }

  @PostMapping("/find-by-ids")
  public List<UserDto> findUsersByIds(@RequestBody List<Long> userIds) {
    return ((UserService) service).findUsersByIds(userIds).stream().map(User::toDto).toList();
  }

  @PostMapping("/find-by-options")
  public List<UserDto> findUsersByOptions(@RequestBody UserDto userDto) {
    return ((UserService) service)
            .findUsersByOptions(
                userDto.getIdf(),
                userDto.getUsername(),
                userDto.getFullName(),
                userDto.getEmail(),
                userDto.getMobilePhone(),
                userDto.getDescription(),
                userDto.getAddressDetail())
            .stream()
            .map(User::toDto)
            .toList();
  }

  @PostMapping("/search")
  public FastMap searchUsers(@RequestBody FastMap condition) {
    String filterText = condition.getString("filterText");
    Boolean active = condition.getBoolean("active");
    List<Long> departments = condition.getList("departments");
    List<Long> roles = condition.getList("roles");
    Integer page = condition.getInt("page");
    Integer pageSize = condition.getInt("pageSize");
    Page<UserDto> userPage =
        ((UserService) service)
            .searchUsers(filterText, active, departments, roles, page, pageSize)
            .map(User::toDto);
    return FastMap.create()
        .add("rows", userPage.getContent())
        .add("total", userPage.getTotalElements())
        .add("pageSize", userPage.getSize())
        .add("page", userPage.getNumber());
  }

  @Override
  @PostMapping({"/sync-all"})
  public void syncAll(@RequestBody List<UserDto> dtos) {
    List<Department> departments = cacheEntityService.getDepartments();
    Department defaultDepartment =
        departments.stream()
            .filter(d -> "SALE".equals(d.getDepartmentName()))
            .findFirst()
            .orElse(null);
    List<Role> roles = cacheEntityService.getRoles();
    Role defaultRole =
        roles.stream().filter(r -> "HUMAN".equals(r.getRoleName())).findFirst().orElse(null);
    this.service.sync(
        dtos.stream()
            .map(
                userDto -> {
                  User user = BeanMapper.instance().map(userDto, User.class);
                  user.setDepartment(
                      departments.stream()
                          .filter(
                              department -> department.getId().equals(userDto.getDepartmentId()))
                          .findFirst()
                          .orElse(defaultDepartment));
                  user.setRole(
                      roles.stream()
                          .filter(role -> role.getId().equals(userDto.getRoleId()))
                          .findFirst()
                          .orElse(defaultRole));
                  return user;
                })
            .toList());
  }
}
