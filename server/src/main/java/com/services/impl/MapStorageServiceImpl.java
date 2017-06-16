package com.services.impl;

import com.dto.UserModel;
import com.services.StorageService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Oleksandr on 7/3/2017.
 */
@Service
public class MapStorageServiceImpl implements StorageService {

    private static Map<Long, UserModel> storageMap = new HashMap<>();

    static {
        storageMap.put(0L, new UserModel(0, "Default string"));
    }

    @Override
    public UserModel getDataById(long id) {
        UserModel userModel = storageMap.get(id);
        if (userModel == null) {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }
        return userModel;
    }

    @Override
    public long putData(UserModel userModel) {
        storageMap.put(userModel.getId(), userModel);
        UserModel result = storageMap.get(userModel.getId());
        return result.getId();
    }

    @Override
    public List<UserModel> getAllUsers() {
        return storageMap.values().stream()
                .collect(Collectors.toList());
    }
}
