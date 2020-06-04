package com.anzhiyule.transfer.controller;

import com.anzhiyule.transfer.model.User;
import com.anzhiyule.transfer.model.dto.UserDTO;
import com.anzhiyule.transfer.service.IUserService;
import com.anzhiyule.transfer.tools.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user/")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "user/details", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> details() {
        return ResponseEntity.ok(CurrentUser.getUser());
    }

    @GetMapping(value = "user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO[]> queryUsers() {
        User[] users = userService.listUsers();
        return ResponseEntity.ok(UserDTO.toDTO(users));
    }

    @GetMapping(value = "user/folder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO[]> queryUsersByFolder(@RequestParam String folderId) {
        User[] users = userService.listUsersByFolderId(folderId);
        return ResponseEntity.ok(UserDTO.toDTO(users));
    }

    @PutMapping(value = "user", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> updatePassword(@RequestParam String opd, @RequestParam String npd) {
        String result = userService.updatePassword(opd, npd);
        return ResponseEntity.ok(result);
    }
}
