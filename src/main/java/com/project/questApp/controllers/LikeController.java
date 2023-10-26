package com.project.questApp.controllers;

import com.project.questApp.entities.Like;
import com.project.questApp.request.LikeCreateRequest;
import com.project.questApp.response.LikeResponse;
import com.project.questApp.services.LikeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
public class LikeController {
    private LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public List<LikeResponse> getAllLikes(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
        return likeService.getAllLikes(userId,postId);
    }
    @PostMapping
    public Like createOneLike(@RequestBody LikeCreateRequest request){
        return likeService.createOneLike(request);
    }
    @GetMapping("/{likedId}")
    public Like getOneLikeById(@PathVariable Long likeId){
        return likeService.getOneLikeById(likeId);
    }
    @DeleteMapping("/{likeId}")
    public void deleteOneLikeById(@PathVariable Long likeId){
        likeService.deleteOneLikeById(likeId);
    }
}
