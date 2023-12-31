package com.project.questApp.services;

import com.project.questApp.entities.Comment;
import com.project.questApp.entities.Like;
import com.project.questApp.entities.User;
import com.project.questApp.repos.CommentRepository;
import com.project.questApp.repos.LikeRepository;
import com.project.questApp.repos.PostRepository;
import com.project.questApp.repos.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;
    LikeRepository likeRepository;
    CommentRepository commentRepository;
    PostRepository postRepository;

    public UserService(UserRepository userRepository,LikeRepository likeRepository,CommentRepository commentRepository,PostRepository postRepository) {
        this.userRepository = userRepository;
        this.likeRepository=likeRepository;
        this.commentRepository=commentRepository;
        this.postRepository=postRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveOneUser(User newUser) {
        return userRepository.save(newUser);
    }

    public User getOneUserById(long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateOneUser(long userId, User newUser) {
        Optional<User> user=userRepository.findById(userId);
        if(user.isPresent()){
            User foundUser=user.get();
            foundUser.setUserName(newUser.getUserName());
            foundUser.setPassword(newUser.getPassword());
            userRepository.save(foundUser);
            return foundUser;
        }else{
            return null;
        }
    }

    public void deleteOneUser(long userId) {
        userRepository.deleteById(userId);
    }

    public User getOneUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public List<Object> getUserActivity(Long userId) {
        List<Long> postIds= postRepository.findTopByUserId(userId);
        if(postIds.isEmpty())
            return null;
        List<Comment> comments=commentRepository.findUserCommentsByPostId(postIds);
        List<Like> likes= likeRepository.findUserLikesByPostId(postIds);
        List<Object> result=new ArrayList<>();
        result.addAll(comments);
        result.addAll(likes);
        return result;
    }
}
