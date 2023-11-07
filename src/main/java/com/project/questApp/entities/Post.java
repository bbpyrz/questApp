package com.project.questApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "post")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//otomatik id üretiyor
    long id;
    /*@ManyToOne(fetch = FetchType.LAZY)//user objesini hemen çekme
    @JsonIgnore*/
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_Id",nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)//Bir user silindiği zaman postlarda silinsin
    User user;
    String title;
    @Lob
    @Column(columnDefinition = "text")
    String text;
    @Temporal(TemporalType.TIMESTAMP)
    Date createDate;
}
