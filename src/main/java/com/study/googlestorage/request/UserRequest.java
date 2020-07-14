package com.study.googlestorage.request;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserRequest {
    @NotNull(message = "Enter a name, please.")
    private String name;

    public String getName() {
        return name;
    }
}
