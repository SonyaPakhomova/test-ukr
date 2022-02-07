package com.example.testurk.shopTest.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class User {
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private Boolean accountStatus;
}
