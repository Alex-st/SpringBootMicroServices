package com.services.impl;

import com.dto.UserDto;
import com.feignclient.ServerFeignClient;
import com.services.MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Oleksandr on 6/16/2017.
 */
@Slf4j
@Service
public class MainServiceImpl implements MainService {

    private final static String URI_STRING = "http://localhost:8080/server";

    @Autowired
    ServerFeignClient serverFeignClient;

    @Override
    public UserDto getUserDataById(long id) {

//        String uri = URI_STRING + "/user/{id}";
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        Map<String, String> params = new HashMap<>();
//        params.put("id", String.valueOf(id));
//
//        UserDto result = restTemplate.getForObject(uri, UserDto.class, params);
        UserDto result = serverFeignClient.getUser(id);

        log.info("User {} was successfully fetched", result.getUsername());

        return result;
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<UserDto> result = serverFeignClient.getAllUsers();

//        URI uri = UriComponentsBuilder.fromUriString(URI_STRING + "/user")
////                .pathSegment("queues", "{vhost}")
////                .buildAndExpand(vhost).encode()
//                .build()
//                .toUri();
//
//        RestTemplate restTemplate = new RestTemplate();
//        List<UserDto> result = restTemplate.getForObject(uri, List.class);

        log.info("{} were fetched", result.size());

        return result;
    }

    @Override
    public long saveUser(UserDto dto) {

//        URI uri = UriComponentsBuilder.fromUriString(URI_STRING + "/user")
//                .build()
//                .toUri();
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        UserDto result = restTemplate.postForObject(uri, dto, UserDto.class);

        Long userId = serverFeignClient.saveUser(dto);
        log.info("User {} was successfully created", userId);

        return userId;
    }
}
