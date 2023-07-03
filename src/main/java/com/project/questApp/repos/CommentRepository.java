package com.project.questApp.repos;

import com.project.questApp.entities.Comment;
import com.project.questApp.request.CommentUpdateRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByUserIdAndPostId(Long userId, Long postId);
    List<Comment> findByUserId(Long userId);

    List<Comment> findByPostId(Long postId);

    Comment updateOneCommentById(Long commentId, CommentUpdateRequest commentUpdateRequest);
}
