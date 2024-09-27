package vn.nht.social.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.nht.social.Model.Chat;
import vn.nht.social.Model.User;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
    public List<Chat> findByUsersId(Integer userId);

    @Query("select c from Chat c where :user member of c.users and :reqUser member of c.users")
    public Chat findChatByUsersId(@Param("user")User user,@Param("reqUser")User reqUser);
}
