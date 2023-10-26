package com.project.questApp.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//otomatik id üretiyor
    long id;
    String userName;
    String password;
}
