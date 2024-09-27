package vn.nht.social.Service;

import vn.nht.social.Model.Comment;

public interface CommentService {
    public Comment createComment(Comment comment,Integer postId,Integer userId) throws Exception;
    public Comment likeComment(Integer commentId,Integer userId) throws Exception;
    public Comment findCommentById(Integer commentId) throws Exception;
}
