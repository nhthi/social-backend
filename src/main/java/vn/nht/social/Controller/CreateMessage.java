package vn.nht.social.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.nht.social.Model.Chat;
import vn.nht.social.Model.Message;
import vn.nht.social.Model.User;
import vn.nht.social.Service.ChatService;
import vn.nht.social.Service.MessageService;
import vn.nht.social.Service.UserService;

import java.util.List;

@RestController
public class CreateMessage {

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;

    @PostMapping("/api/message/chat/{chatId}")
    public Message createMessage(@RequestBody Message req,
                                 @RequestHeader("Authorization")String jwt,
                                 @PathVariable("chatId") Integer chatId) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Message message = messageService.createMessage(user,chatId,req);
        return message;
    }

    @GetMapping("/api/message/chat/{chatId}")
    public List<Message> findChatsMessage(@RequestHeader("Authorization")String jwt,
                                          @PathVariable("chatId") Integer chatId) throws Exception {
        User user = userService.findUserByJwt(jwt);
        List<Message> messages= messageService.findChatsMessages(chatId);
        return messages;
    }
}
