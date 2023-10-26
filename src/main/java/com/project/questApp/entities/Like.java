package com.project.questApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "p_like")
@Data
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//otomatik id üretiyor
    long id;
    @ManyToOne(fetch = FetchType.LAZY)//user objesini hemen çekme
    @JoinColumn(name = "post_Id",nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)//Bir usersilindiği zaman postlarda silinsin
    @JsonIgnore
    Post post;
    @ManyToOne(fetch = FetchType.LAZY)//user objesini hemen çekme
    @JoinColumn(name = "user_Id",nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)//Bir usersilindiği zaman postlarda silinsin
    @JsonIgnore
    User user;
}
