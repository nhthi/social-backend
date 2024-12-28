package vn.nht.social.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.nht.social.Model.Post;
import vn.nht.social.Model.User;
import vn.nht.social.Service.PostService;
import vn.nht.social.Service.UserService;
import vn.nht.social.response.ApiResponse;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @PostMapping("/api/posts")
    public ResponseEntity<Post> createPost(@RequestHeader("Authorization")String jwt,@RequestBody Post post) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        Post createNewPost = postService.createNewPost(post, reqUser.getId());
        return new ResponseEntity<>(createNewPost, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/api/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId,
                                                  @RequestHeader("Authorization")String jwt) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        String message = postService.deletePost(postId, reqUser.getId());
        ApiResponse res = new ApiResponse(message,true);
        return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
    }

    @GetMapping("/api/posts/{postId}")
    public ResponseEntity<Post> findPostByIdHandle(@PathVariable Integer postId) throws Exception {
        Post post = postService.findPostById(postId);
        return new ResponseEntity<Post>(post,HttpStatus.ACCEPTED);
    }

    @GetMapping("/api/posts/user/{userId}")
    public ResponseEntity<List<Post>> findUserPosts(@PathVariable Integer userId) throws Exception {
        List<Post> posts = postService.findPostByUserId(userId) ;
        return new ResponseEntity<List<Post>>(posts,HttpStatus.ACCEPTED);
    }

    @GetMapping("/api/posts")
    public ResponseEntity<List<Post>> findAllPosts()  {
        List<Post> posts = postService.findAllPost();
        return new ResponseEntity<List<Post>>(posts,HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/posts/save/{postId}")
    public ResponseEntity<Post> savedPostHandler(@PathVariable Integer postId,
                                                 @RequestHeader("Authorization")String jwt) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        Post post = postService.savePost(postId,reqUser.getId());
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    @PutMapping("/api/posts/like/{postId}")
    public ResponseEntity<Post> likePostHandler(@PathVariable Integer postId,
                                                @RequestHeader("Authorization")String jwt) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        Post post = postService.likePost(postId, reqUser.getId());
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }
}

