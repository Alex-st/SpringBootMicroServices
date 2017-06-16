package com.services;

import com.dto.UserDto;

import java.util.List;

/**
 * Created by Oleksandr on 6/16/2017.
 */
public interface MainService {

    UserDto getUserDataById(long id);
    List<UserDto> getAllUsers();
    long saveUser(UserDto dto);
}
