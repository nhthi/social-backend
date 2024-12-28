package vn.nht.social.Service;

import vn.nht.social.Model.Reels;

import java.util.List;

public interface ReelsService {
    public Reels createReel(Reels reel,Integer userId) throws Exception;

    public List<Reels> findAllReels();

    public List<Reels> findUserReels(Integer userId) throws Exception;
}
