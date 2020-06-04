package com.anzhiyule.transfer.controller;

import com.anzhiyule.transfer.model.User;
import com.anzhiyule.transfer.service.IUserService;
import com.anzhiyule.transfer.task.TransferDestroyTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/admin/")
public class AdminController {

    private final IUserService userService;

    @Autowired
    public AdminController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "user", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> addUser(@RequestBody User user) {
        String id = userService.addUser(user);
        return ResponseEntity.ok(id);
    }

    @PutMapping(value = "period", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> setPeriod(@RequestParam Integer time) {
        TransferDestroyTask.setPeriod(time);
        return ResponseEntity.ok("success");
    }

    @GetMapping(value = "period", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getPeriod() {
        return ResponseEntity.ok(TransferDestroyTask.getPeriod() + "");
    }
}
