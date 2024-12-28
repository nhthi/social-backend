package vn.nht.social.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.nht.social.Model.Post;

import java.util.List;

public interface PostReository extends JpaRepository<Post, Integer> {

    @Query("select p from Post p where p.user.id =:userId")
    List<Post> findPostByUserId(@Param("userId") Integer userId);
}
