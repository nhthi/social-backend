package vn.nht.social.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.nht.social.Model.Story;
import vn.nht.social.Model.User;
import vn.nht.social.Service.StoryService;
import vn.nht.social.Service.UserService;

import java.util.List;

@RestController
public class StoryController {
    @Autowired
    private StoryService storyService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/story")
    public Story createStory(@RequestBody Story story, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        return storyService.createStory(story,user.getId());
    }

    @GetMapping("/api/story/user/{userId}")
    public List<Story> findStoryByUserId(@PathVariable("userId") Integer userId) throws Exception {
        return storyService.findStoryByUserId(userId);
    }
}
