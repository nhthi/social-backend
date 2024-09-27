package vn.nht.social.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.nht.social.Model.Story;
import vn.nht.social.Model.User;
import vn.nht.social.Repository.StoryRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoryServiceImplementation implements StoryService {
    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private UserService userService;

    @Override
    public Story createStory(Story story, Integer userId) throws Exception {
        User user = userService.findUserById(userId);
        Story createStory = new Story();
        createStory.setCaption(story.getCaption());
        createStory.setImage(story.getImage());
        createStory.setTimestamp(LocalDateTime.now());
        createStory.setUser(user);
        return storyRepository.save(createStory);
    }

    @Override
    public List<Story> findStoryByUserId(Integer userId) throws Exception {
        User user = userService.findUserById(userId);
        return storyRepository.findByUserId(userId);
    }
}
