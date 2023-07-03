package com.project.questApp.request;

import lombok.Data;

@Data
public class PostCreateRequest {
    long id;
    String text;
    String title;
    long userId;

}
