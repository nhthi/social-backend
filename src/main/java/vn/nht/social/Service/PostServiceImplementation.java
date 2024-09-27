package vn.nht.social.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.nht.social.Model.Post;
import vn.nht.social.Model.User;
import vn.nht.social.Repository.PostReository;
import vn.nht.social.Repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImplementation implements PostService{

    @Autowired
    PostReository postReository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;
    @Override
    public Post createNewPost(Post post, Integer userId) throws Exception {
        User user = userService.findUserById(userId);
        Post newPost = new Post();
        newPost.setCaption(post.getCaption());
        newPost.setImage(post.getImage());
        newPost.setVideo(post.getVideo());
        newPost.setUser(user);
        newPost.setCreatedAt(LocalDateTime.now());
        return postReository.save(newPost);
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws Exception {
        User user = userService.findUserById(userId);
        Post post = findPostById(postId);
        if(post.getUser().getId() != user.getId()) {
            throw new Exception("You Can't delete another user post !!!");
        }
        postReository.delete(post);
        return "Post Deleted Successfully";
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) {
        return postReository.findPostByUserId(userId);
    }

    @Override
    public Post findPostById(Integer postId) throws Exception {
        Optional<Post> opt = postReository.findById(postId);
        if(opt.isEmpty()){
            throw new Exception("Post not found with postId : "+postId);
        }
        return opt.get();
    }

    @Override
    public List<Post> findAllPost() {
        return postReository.findAll();
    }

    @Override
    public Post savePost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if(user.getSavedPosts().contains(post)){
            user.getSavedPosts().remove(post);
        }else
            user.getSavedPosts().add(post);
        userRepository.save(user);
        return post;
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if(post.getLikedPost().contains(user)){
            post.getLikedPost().remove(user);
        }else post.getLikedPost().add(user);

        return postReository.save(post);
    }
}
