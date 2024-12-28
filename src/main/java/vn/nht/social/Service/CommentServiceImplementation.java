package vn.nht.social.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.nht.social.Model.Comment;
import vn.nht.social.Model.Post;
import vn.nht.social.Model.User;
import vn.nht.social.Repository.CommentRepository;
import vn.nht.social.Repository.PostReository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImplementation implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostReository postReository;
    @Override
    public Comment createComment(Comment comment, Integer postId, Integer userId) throws Exception {
        User user = userService.findUserById(userId);

        Post post = postService.findPostById(postId);

        comment.setUser(user);
        comment.setContent(comment.getContent());
        comment.setCreateAt(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);
        post.getComments().add(savedComment);
        postReository.save(post);
        return savedComment;
    }

    @Override
    public Comment likeComment(Integer commentId, Integer userId) throws Exception {
        Comment comment = findCommentById(commentId);
        User user = userService.findUserById(userId);

        if(!comment.getLiked().contains(user)){
            comment.getLiked().add(user);
        }else{
            comment.getLiked().remove(user);
        }
        return commentRepository.save(comment);
    }

    @Override
    public Comment findCommentById(Integer commentId) throws Exception {
        Optional<Comment> opt = commentRepository.findById(commentId);
        if(opt.isEmpty()){
            throw new Exception("Comment not exist");
        }
        return opt.get();
    }
}
