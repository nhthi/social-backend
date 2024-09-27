package vn.nht.social.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.nht.social.Model.Reels;
import vn.nht.social.Model.User;
import vn.nht.social.Repository.ReelsRepository;

import java.util.List;

@Service
public class ReelsServiceImplementation implements ReelsService {
    @Autowired
    private UserService userService;
    @Autowired
    private ReelsRepository reelsRepository;

    @Override
    public Reels createReel(Reels reel, Integer userId) throws Exception {
        User user = userService.findUserById(userId);
        Reels createdReel = new Reels();
        createdReel.setTitle(reel.getTitle());
        createdReel.setVideo(reel.getVideo());
        createdReel.setUser(user);
        return reelsRepository.save(createdReel);
    }

    @Override
    public List<Reels> findAllReels() {
        return reelsRepository.findAll();
    }

    @Override
    public List<Reels> findUserReels(Integer userId) throws Exception {
        User user = userService.findUserById(userId);
        return reelsRepository.findByUserId(user.getId());
    }
}
