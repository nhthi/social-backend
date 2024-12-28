package vn.nht.social.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.nht.social.Config.JwtProvider;
import vn.nht.social.Model.User;
import vn.nht.social.Repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public  class UserServiceImplementation implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        User userNew = new User();
        userNew.setEmail(user.getEmail());
        userNew.setPassword(user.getPassword());
        userNew.setFirstName(user.getFirstName());
        userNew.setLastName(user.getLastName());
        userNew.setGender(user.getGender());
        userNew.setAvatar(null);
        userNew.setBackground(null);
        User userSaved = userRepository.save(userNew);
        return userSaved;
    }

    @Override
    public User findUserById(int id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get();
        }
        throw new Exception("user not exist with userId: "+id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User followUser(int userId1, int userId2) throws Exception {
        User user1 = findUserById(userId1);
        User user2 = findUserById(userId2);

        if(user1.getFollowings().contains(user2)) {
            user1.getFollowings().remove(user2);
            user2.getFollowers().remove(user1);
        }else{
            user2.getFollowers().add(user1);
            user1.getFollowings().add(user2);
        }


        userRepository.save(user1);
        userRepository.save(user2);
        return user1;
    }

    @Override
    public User updateUser(User user, Integer id) throws Exception {
        Optional<User> user1 =  userRepository.findById(id);

        if(user1.isEmpty()) {
            throw new Exception("User not exist with userId :"+id);
        }
        User userOld = user1.get();
        if(user.getFirstName()!=null){
            userOld.setFirstName(user.getFirstName());
        }
        if(user.getLastName()!=null){
            userOld.setLastName(user.getLastName());
        }
        if(user.getEmail()!=null){
            userOld.setEmail(user.getEmail());
        }
        if(user.getGender()!=null){
            userOld.setGender(user.getGender());
        }
        if(user.getAvatar()!=null){
            userOld.setAvatar(user.getAvatar());
        }
        if(user.getBackground()!=null){
            userOld.setBackground(user.getBackground());
        }
        User updatedUser = userRepository.save(userOld);
        return updatedUser;
    }

    @Override
    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }

    @Override
    public User findUserByJwt(String jwt) {
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public List<User> getFollowingsById(int id) throws Exception {
        User user = findUserById(id);
        return user.getFollowings();
    }
    @Override
    public List<User> getFollowersById(int id) throws Exception {
        User user = findUserById(id);
        return user.getFollowers();
    }
}
