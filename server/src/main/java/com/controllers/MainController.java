package com.controllers;

import com.dto.UserModel;
import com.services.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Created by Oleksandr on 7/3/2017.
 */
@Slf4j
@RestController
public class MainController {

    @Autowired
    private StorageService storageService;

    /**
     * @return Returns single {@link String}
     * @description Search for user by its username.
     * @httpMethod GET
     * @httpUrl http://{host}:{port}/server/user/:id
     * @httpUrlExample http://localhost:8080/server/user/0
     * @returnType application/json
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserModel> getStringById(final @PathVariable("id") Long id) {
        log.debug("Get user with id {} in MainController", id);

        UserModel user = storageService.getDataById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * @return Returns list of {@link UserModel}
     * @description Return all users.
     * @httpMethod GET
     * @httpUrl http://{host}:{port}/server/user
     * @httpUrlExample http://localhost:8080/server/user
     * @returnType application/json
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<UserModel>> getAllUsers() {
        log.debug("GetAllUsers MainController entry point");
        return new ResponseEntity<>(storageService.getAllUsers(), HttpStatus.OK);
    }

    /**
     * @description Method to create new user
     * @httpMethod POST
     * @httpUrl http://{host}:{port}/server/user
     * @httpUrlExample http://localhost:8080/server/user/1
     * @requestBodyExample {"id" : "4", "username" : "Maya007"}
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<Long> saveUser(@RequestBody @Valid final UserModel userModel) {
        log.debug("Put user with id {} and username {} in MainController", userModel.getId(), userModel.getUsername());
        Long id = storageService.putData(userModel);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    ResponseEntity<String> handleNotFoundRequests(Exception ex) throws IOException {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
