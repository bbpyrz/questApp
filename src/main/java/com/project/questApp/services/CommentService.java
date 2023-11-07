package com.project.questApp.services;

import com.project.questApp.entities.Comment;
import com.project.questApp.entities.Post;
import com.project.questApp.entities.User;
import com.project.questApp.repos.CommentRepository;
import com.project.questApp.request.CommentCreateRequest;
import com.project.questApp.request.CommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    public List<Comment> getAllComments(Optional<Long> userId, Optional<Long> postId) {
        if(userId.isPresent()&& postId.isPresent()){
            return commentRepository.findByUserIdAndPostId(userId.get(),postId.get());
        }else if(userId.isPresent()){
            return commentRepository.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            return commentRepository.findByPostId(postId.get());
        }else{
            return commentRepository.findAll();
        }

    }

    public Comment getOneComment(long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createOneComment(CommentCreateRequest request) {
        User user=userService.getOneUserById(request.getUserId());
        Post post=postService.getOnePostById(request.getPostId());
        if(user!=null&&post!=null){
            Comment commentToSave=new Comment();
            commentToSave.setId(request.getId());
            commentToSave.setPost(post);
            commentToSave.setUser(user);
            commentToSave.setText(request.getText());
            commentToSave.setCreateDate(new Date());
            return commentRepository.save(commentToSave);
        }else
            return null;
    }

    public Comment updateOneCommentById(Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Optional<Comment> comment=commentRepository.findById(commentId);
        if(comment.isPresent()){
            Comment commentToUpdate=comment.get();
            commentToUpdate.setText(commentUpdateRequest.getText());
            return commentRepository.save(commentToUpdate);
        }
        else
            return null;
    }

    public void deleteOneCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
