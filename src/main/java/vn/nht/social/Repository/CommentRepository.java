package vn.nht.social.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.nht.social.Model.Comment;
import vn.nht.social.Model.Reels;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
