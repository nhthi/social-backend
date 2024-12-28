package vn.nht.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.nht.social.Controller.AuthController;
import vn.nht.social.Model.User;
import vn.nht.social.Repository.UserRepository;
import vn.nht.social.Service.UserService;

@SpringBootApplication
public class SocialApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialApplication.class, args);
    }
//@Autowired
//UserRepository userRepository;
//    @Bean
//	public CommandLineRunner commandLineRunner(UserService userService){
//		return runner-> {
//
//            AuthController authController = new AuthController();
//            User user = new User();
//            user.setEmail("thinhkhunglatao");
//            authController.createUser(user);
//        };
//	    }
}
