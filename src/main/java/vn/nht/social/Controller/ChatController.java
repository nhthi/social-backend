package vn.nht.social.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.nht.social.Model.Chat;
import vn.nht.social.Model.User;
import vn.nht.social.Request.CreateChatRequest;
import vn.nht.social.Service.ChatService;
import vn.nht.social.Service.UserService;

import java.util.List;

@RestController
public class ChatController {
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;

    @PostMapping("/api/chats")
    public Chat createChat(@RequestHeader("Authorization")String jwt,@RequestBody CreateChatRequest req) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        User user2 = userService.findUserById(req.getUserId());
        Chat chat = chatService.createChat(reqUser,user2);
        return chat;
    }

    @GetMapping("/api/chats")
    public List<Chat> findUsersChat(@RequestHeader("Authorization")String jwt) {
        User user = userService.findUserByJwt(jwt);
        return chatService.findUsersChat(user.getId());
    }

}
