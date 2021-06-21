package com.example.javademo.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class ResponseError {
    @JsonProperty("title")
    String title;

    @JsonProperty("detail")
    String detail;

    @JsonProperty("code")
    String code;
}