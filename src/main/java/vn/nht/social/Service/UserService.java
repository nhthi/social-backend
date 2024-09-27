package vn.nht.social.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import vn.nht.social.Model.User;

import java.util.List;

public interface UserService {
    public User registerUser(User user);
    public User findUserById(int id) throws Exception;
    public User findUserByEmail(String email);
    public User followUser(int userId1, int userId2) throws Exception;
    public User updateUser(User user,Integer id) throws Exception;
    public List<User> searchUser(String query);
    public User findUserByJwt(String jwt);
    public List<User> getFollowingsById(int id) throws Exception;
    public List<User> getFollowersById(int id) throws Exception;

}
