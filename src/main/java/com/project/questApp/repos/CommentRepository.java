package com.project.questApp.repos;

import com.project.questApp.entities.Comment;
import com.project.questApp.request.CommentUpdateRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByUserIdAndPostId(Long userId, Long postId);
    List<Comment> findByUserId(Long userId);

    List<Comment> findByPostId(Long postId);

    @Query(value = "select * from comment where post_id= :postIds limit 5",nativeQuery = true)
    List<Comment> findUserCommentsByPostId(@Param("postIds") List<Long> postIds);

}
