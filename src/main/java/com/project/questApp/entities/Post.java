package com.project.questApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "post")
@Data
public class Post {
    @Id
    long id;
    @ManyToOne(fetch = FetchType.LAZY)//user objesini hemen çekme
    @JoinColumn(name = "user_Id",nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)//Bir usersilindiği zaman postlarda silinsin
    @JsonIgnore
    User user;
    String title;
    @Lob
    @Column(columnDefinition = "text")
    String text;
}
