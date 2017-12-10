package com.controllers;

import com.dto.UserDto;
import com.services.impl.MainServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Oleksandr on 6/16/2017.
 */
@Slf4j
@RestController
public class MainController {

    @Autowired
    private MainServiceImpl mainService;

    /**
     * @return Returns single {@link String}
     * @description Search for user by its username.
     * @httpMethod GET
     * @httpUrl http://{host}:{port}/client/user/:id
     * @httpUrlExample http://localhost:8081/client/user/0
     * @returnType application/json
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getStringById(final @PathVariable("id") Integer id) {
        log.debug("Get user with id {} in MainController", id);

        UserDto user = mainService.getUserDataById(id);
        if (user != null) {
            return new ResponseEntity<UserDto>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * @return Returns list of {@link UserDto}
     * @description Return all users.
     * @httpMethod GET
     * @httpUrl http://{host}:{port}/client/user
     * @httpUrlExample http://localhost:8081/client/user
     * @returnType application/json
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.debug("GetAllUsers MainController entry point");
        return new ResponseEntity<>(mainService.getAllUsers(), HttpStatus.OK);
    }

    /**
     * @description Method to create new user
     * @httpMethod POST
     * @httpUrl http://{host}:{port}/client/user
     * @httpUrlExample http://localhost:8081/client/user
     * @requestBodyExample {"id" : "4", "username" : "Maya007"}
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<Void> saveUser(@RequestBody @Valid final UserDto userDto) {
        log.debug("Put user with id {} and username {} in MainController", userDto.getId(), userDto.getUsername());
        mainService.saveUser(userDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
