package com.feignclient;

import com.dto.UserDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by alex on 7/21/2017.
 */
@FeignClient("server")
public interface ServerFeignClient {

    @RequestMapping(value = "/server/user", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
    List<UserDto> getAllUsers();

    @RequestMapping(value = "/server/user/{id}", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
    UserDto getUser(final @PathVariable("id") long id);

    @RequestMapping(value = "/server/user", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
    Long saveUser(@RequestBody final UserDto userDto);

}
