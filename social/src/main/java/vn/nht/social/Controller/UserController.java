package vn.nht.social.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.nht.social.Model.User;
import vn.nht.social.Repository.UserRepository;
import vn.nht.social.Service.UserService;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;



    @GetMapping("/api/users")
    public List<User> getALLUser(){
        return userRepository.findAll();
    }

    @GetMapping("/api/users/{id}")
    public User getUserById(@PathVariable int id) throws Exception {
        User user = userService.findUserById(id);
        user.setPassword(null);
        return user;
    }

    @PutMapping("/api/users")
    public User updateUser(@RequestHeader("Authorization")String jwt,@RequestBody User user) throws Exception{
        User reqUser = userService.findUserByJwt(jwt);
        return userService.updateUser(user, reqUser.getId());
    }

//    @DeleteMapping("/api/{id}")
//    public String deleteUser(@PathVariable Integer id) throws Exception{
//
//        Optional<User> user1 =  userRepository.findById(id);
//
//        if(user1.isEmpty()) {
//            throw new Exception("User not exist with userId :"+id);
//        }
//
//
//        userRepository.delete(user1.get());
//        return "Deleted user with id "+id;
//    }
    @PutMapping("/api/users/follow/{userId1}/{userId2}")
    public List<User> followUserHandler(@PathVariable Integer userId1, @PathVariable Integer userId2) throws Exception {
            return userService.followUser(userId1, userId2).getFollowings();
    }

    @GetMapping("/api/users/followings/{userId}")
    public List<User> getFollowingsById(@PathVariable("userId") int id) throws Exception {
        return userService.getFollowingsById(id);
    }
    @GetMapping("/api/users/followers/{userId}")
    public List<User> getFollowersById(@PathVariable("userId") int id) throws Exception {
        return userService.getFollowersById(id);
    }

    @GetMapping("/users/search")
    public List<User> searchUser(@RequestParam String query) {
        return userService.searchUser(query);
    }

    @GetMapping("/api/users/profile")
    public User findUserByJwt(@RequestHeader("Authorization")String jwt){
        User user = userService.findUserByJwt(jwt);
        user.setPassword(null);
        return user;
    }
}
