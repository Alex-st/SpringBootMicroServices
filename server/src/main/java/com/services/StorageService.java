package com.services;

import com.dto.UserModel;

import java.util.List;

/**
 * Created by Oleksandr on 7/3/2017.
 */
public interface StorageService {
    UserModel getDataById(long i);
    long putData(UserModel userModel);
    List<UserModel> getAllUsers();
}
