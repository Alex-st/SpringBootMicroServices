package com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Oleksandr on 7/4/2017.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private long id;
    private String username;
}
