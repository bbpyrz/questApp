package com.project.questApp.services;

import com.project.questApp.entities.Like;
import com.project.questApp.entities.Post;
import com.project.questApp.entities.User;
import com.project.questApp.repos.PostRepository;
import com.project.questApp.request.PostCreateRequest;
import com.project.questApp.request.PostUpdateRequest;
import com.project.questApp.response.LikeResponse;
import com.project.questApp.response.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private PostRepository postRepository;
    private LikeService likeService;
    private UserService userService;
    @Autowired
    public PostService(PostRepository postRepository, UserService userService, @Lazy LikeService likeService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.likeService=likeService;
    }

    public List<PostResponse> getAllPosts(Optional<Long> userId) {
        List<Post> list;
        if(userId.isPresent()){
            list=postRepository.findByUserId(userId.get());

        }else{
            list=postRepository.findAll();
        }
        return list.stream().map(p->{
            List<LikeResponse> likes=likeService.getAllLikes(Optional.ofNullable(null),Optional.of(p.getId()));
            return new PostResponse(p,likes);}).collect(Collectors.toList());

    }

    public Post getOnePostById(long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Post createOnePost(PostCreateRequest newPostRequest) {
        User user=userService.getOneUserById(newPostRequest.getUserId());
        if(user==null){
            return null;
        }
        Post toSave=new Post();
        toSave.setId(newPostRequest.getId());
        toSave.setText(newPostRequest.getText());
        toSave.setTitle(newPostRequest.getTitle());
        toSave.setUser(user);
        return postRepository.save(toSave);
    }

    public Post updateOnePostById(long postId, PostUpdateRequest updatePost) {
        Optional<Post> post=postRepository.findById(postId);
        if(post.isPresent()){
            Post toUpdate=post.get();
            toUpdate.setText(updatePost.getText());
            toUpdate.setTitle(updatePost.getTitle());
            return postRepository.save(toUpdate);
        }
        return null;
    }

    public void deleteOnePostById(long postId) {
        postRepository.deleteById(postId);
    }
}
