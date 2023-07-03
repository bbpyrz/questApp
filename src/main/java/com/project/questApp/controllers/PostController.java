package com.project.questApp.controllers;

import com.project.questApp.entities.Post;
import com.project.questApp.request.PostCreateRequest;
import com.project.questApp.request.PostUpdateRequest;
import com.project.questApp.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {this.postService = postService;}
    @GetMapping
    public List<Post> getAllPosts(@RequestParam Optional<Long> userId){
        return postService.getAllPosts(userId);
    }
    @PostMapping
    public Post createOnePost(@RequestBody PostCreateRequest newPostRequest){
        return postService.createOnePost(newPostRequest);
    }
    @GetMapping("/{postId}")
    public Post getOnePost(@PathVariable long postId){
        return postService.getOnePostById(postId);
    }
    @PutMapping("/{postId}")
    public Post updateOnePost(@PathVariable long postId, @RequestBody PostUpdateRequest updateRequest){
        return postService.updateOnePostById(postId,updateRequest);
    }
    @DeleteMapping("/{postId}")
    public void deleteOnePost(@PathVariable long postId){
        postService.deleteOnePostById(postId);
    }
}
