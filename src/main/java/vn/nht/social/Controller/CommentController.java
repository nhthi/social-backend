package vn.nht.social.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.nht.social.Model.Comment;
import vn.nht.social.Model.User;
import vn.nht.social.Service.CommentService;
import vn.nht.social.Service.UserService;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/comments/post/{postId}")
    public Comment createComment(@RequestBody Comment comment,
                                 @RequestHeader("Authorization")String jwt,
                                @PathVariable("postId") Integer postId) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Comment createComment = commentService.createComment(comment,postId,user.getId());
        return createComment;
    }

    @PutMapping("/api/comments/like/{commentId}")
    public Comment likedComment(@RequestHeader("Authorization")String jwt,
                                 @PathVariable("commentId") Integer commentId) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Comment likedComment = commentService.likeComment(commentId,user.getId());
        return likedComment;
    }
}
