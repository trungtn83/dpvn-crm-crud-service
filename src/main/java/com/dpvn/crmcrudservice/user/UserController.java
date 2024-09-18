package com.dpvn.crmcrudservice.user;

import com.dpvn.crmcrudservice.AbstractController;
import com.dpvn.crmcrudservice.domain.dto.UserDto;
import com.dpvn.crmcrudservice.domain.entity.User;
import java.util.List;
import java.util.Optional;
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

  @PostMapping("/sync-all")
  public void syncAllUsers(@RequestBody List<UserDto> userDtos) {
    service.sync(userDtos.stream().map(UserDto::toEntity).toList());
  }
}
