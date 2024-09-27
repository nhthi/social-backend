package vn.nht.social.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.nht.social.Model.Chat;
import vn.nht.social.Model.User;
import vn.nht.social.Repository.ChatRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImplementation implements ChatService {


    @Autowired
    private UserService userService;
    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Chat createChat(User reqUser, User user) {
        Chat isExistChat = chatRepository.findChatByUsersId(user,reqUser);
        if(isExistChat != null){
            return isExistChat;
        }
        Chat chat = new Chat();
        chat.getUsers().add(user);
        chat.getUsers().add(reqUser);
        chat.setTimestamp(LocalDateTime.now());
        return chatRepository.save(chat);
    }

    @Override
    public Chat findChatById(Integer id) throws Exception {
        Optional<Chat> opt = chatRepository.findById(id);
        if(opt.isEmpty()){
            throw new Exception("Chat not found with id: "+id);
        }
        return opt.get();
    }

    @Override
    public List<Chat> findUsersChat(Integer userId) {
        return chatRepository.findByUsersId(userId);
    }
}
