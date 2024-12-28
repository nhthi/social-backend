package vn.nht.social.Service;

import jakarta.persistence.criteria.CriteriaBuilder;
import vn.nht.social.Model.Story;

import java.util.List;

public interface StoryService {
    public Story createStory(Story story, Integer userId) throws Exception;

    public List<Story> findStoryByUserId(Integer userId) throws Exception;


}
