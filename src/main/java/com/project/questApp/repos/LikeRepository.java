package com.project.questApp.repos;

import com.project.questApp.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {
    List<Like> findByUserIdAndPostId(Optional<Long> userId, Optional<Long> postId);

    List<Like> findAllByUserId(Optional<Long> userId);

    List<Like> findByPostId(Optional<Long> postId);

    @Query(value = "select * from p_like where post_id=:postIds limit 5",nativeQuery = true)
    List<Like> findUserLikesByPostId(@Param("postIds") List<Long> postIds);
}
