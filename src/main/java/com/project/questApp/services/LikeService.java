package com.project.questApp.services;

import com.project.questApp.entities.Like;
import com.project.questApp.entities.Post;
import com.project.questApp.entities.User;
import com.project.questApp.repos.LikeRepository;
import com.project.questApp.request.LikeCreateRequest;
import com.project.questApp.response.LikeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {
    private LikeRepository likeRepository;
    private UserService userService;
    private PostService postService;

    @Autowired
    public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<LikeResponse> getAllLikes(Optional<Long> userId, Optional<Long> postId) {
        List<Like> list;
        if(userId.isPresent() && postId.isPresent()){
            list= likeRepository.findByUserIdAndPostId(userId,postId);
        }else if(userId.isPresent()){
            list= likeRepository.findAllByUserId(userId);
        }else if(postId.isPresent()){
            list= likeRepository.findByPostId(postId);
        }else{
            list= likeRepository.findAll();
        }
        return list.stream().map(like->new LikeResponse(like)).collect(Collectors.toList());

    }

    public Like getOneLikeById(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    public void deleteOneLikeById(Long likeId) {
        likeRepository.deleteById(likeId);
    }

    public Like createOneLike(LikeCreateRequest request) {
        User user=userService.getOneUserById(request.getUserId());
        Post post=postService.getOnePostById(request.getPostId());
        if(user != null && post !=null){
            Like likeToSave=new Like();
            //likeToSave.setId(request.getId());
            likeToSave.setUser(user);
            likeToSave.setPost(post);
            return likeRepository.save(likeToSave);
        }else
            return null;
    }
}
