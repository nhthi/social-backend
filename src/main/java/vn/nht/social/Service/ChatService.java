package vn.nht.social.Service;

import vn.nht.social.Model.Chat;
import vn.nht.social.Model.User;

import java.util.List;

public interface ChatService {

    public Chat createChat(User reqUser, User user2);

    public Chat findChatById(Integer id) throws Exception;

    public List<Chat> findUsersChat(Integer userId);
}
