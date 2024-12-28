package vn.nht.social.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.nht.social.Model.Reels;

import java.util.List;

public interface ReelsRepository extends JpaRepository<Reels, Integer> {
    public List<Reels> findByUserId(Integer userId);
}
