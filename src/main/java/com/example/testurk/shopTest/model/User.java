package com.example.testurk.shopTest.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private Boolean accountStatus;
}
