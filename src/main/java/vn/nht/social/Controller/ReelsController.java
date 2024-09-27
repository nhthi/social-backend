package vn.nht.social.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.nht.social.Model.Reels;
import vn.nht.social.Model.User;
import vn.nht.social.Service.ReelsService;
import vn.nht.social.Service.UserService;

import java.util.List;

@RestController
public class ReelsController {
    @Autowired
    private ReelsService reelsService;
    @Autowired
    private UserService userService;

    @PostMapping("/api/reels")
    public Reels createReels(@RequestBody Reels reels, @RequestHeader("Authorization") String jwt) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        Reels createReels = reelsService.createReel(reels,reqUser.getId());
        return createReels;
    }

    @GetMapping("/api/reels")
    public List<Reels> findAllReels() {
        return reelsService.findAllReels();
    }

    @GetMapping("/api/reels/user/{userId}")
    public List<Reels> findUserReels(@PathVariable("userId") Integer userId) throws Exception {
        return reelsService.findUserReels(userId);
    }
}
