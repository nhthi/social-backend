package vn.nht.social.Service;

import vn.nht.social.Model.Chat;
import vn.nht.social.Model.Message;
import vn.nht.social.Model.User;

import java.util.List;

public interface MessageService {
    public Message createMessage(User user, Integer chatId, Message message) throws Exception;

    public List<Message> findChatsMessages(Integer chatId) throws Exception;


}
