package vn.nht.social.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.nht.social.Model.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    public List<Message> findByChatId(Integer chatId);
}
