package vn.nht.social.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String content;

    private String image;

    private LocalDateTime timestamp;

    @JsonIgnore
    @ManyToOne
    private Chat chat;

    @ManyToOne
    private User user;
}
