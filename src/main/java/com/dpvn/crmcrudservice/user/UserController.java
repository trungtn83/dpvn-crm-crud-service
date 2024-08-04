package com.dpvn.crmcrudservice.user;

import com.dpvn.crmcrudservice.AbstractController;
import com.dpvn.crmcrudservice.domain.dto.UserDto;
import com.dpvn.crmcrudservice.domain.entity.User;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crmcrud/user")
public class UserController extends AbstractController<User, UserDto> {
  public UserController(UserService userService) {
    super(userService);
  }

  @GetMapping("/username/{username}")
  public ResponseEntity<UserDto> getCrmUserByUserName(@PathVariable String username) {
    Optional<User> user = ((UserService) service).findByUsername(username);
    return user.map(u -> ResponseEntity.ok(u.toDto())).orElseGet(() -> ResponseEntity.notFound().build());
  }
}
