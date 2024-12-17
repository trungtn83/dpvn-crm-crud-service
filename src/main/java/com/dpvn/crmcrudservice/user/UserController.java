package com.dpvn.crmcrudservice.user;

import com.dpvn.crmcrudservice.domain.dto.UserDto;
import com.dpvn.crmcrudservice.domain.entity.User;
import com.dpvn.shared.controller.AbstractController;
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
public class UserController extends AbstractController<User, UserDto> {

  public UserController(UserService userService) {
    super(userService);
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
    List<String> departments = condition.getList("departments");
    List<String> roles = condition.getList("roles");
    Integer page = condition.getInt("page");
    Integer pageSize = condition.getInt("pageSize");
    Page<FastMap> userPage =
        ((UserService) service).searchUsers(filterText, departments, roles, page, pageSize);
    return FastMap.create()
        .add("rows", userPage.getContent())
        .add("total", userPage.getTotalElements())
        .add("pageSize", userPage.getSize())
        .add("page", userPage.getNumber());
  }
}
