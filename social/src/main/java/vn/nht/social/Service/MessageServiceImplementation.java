package vn.nht.social.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.nht.social.Model.Chat;
import vn.nht.social.Model.Message;
import vn.nht.social.Model.User;
import vn.nht.social.Repository.ChatRepository;
import vn.nht.social.Repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImplementation implements MessageService{
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Message createMessage(User user, Integer chatId, Message req) throws Exception {
        Message message = new Message();
        Chat chat = chatService.findChatById(chatId);
        if(!chat.getUsers().contains(user)) {
            throw new Exception("You do not own this chat");
        }
        message.setChat(chat);
        message.setUser(user);
        message.setImage(req.getImage());
        message.setContent(req.getContent());
        message.setTimestamp(LocalDateTime.now());
        chat.getMessages().add(message);
        chatRepository.save(chat);
        return messageRepository.save(message);
    }

    @Override
    public List<Message> findChatsMessages(Integer chatId) throws Exception {
        Chat chat = chatService.findChatById(chatId);
        return messageRepository.findByChatId(chatId);
    }
}
