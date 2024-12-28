package vn.nht.social.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.nht.social.Model.Story;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Integer> {

    public List<Story> findByUserId(Integer userId);
}
