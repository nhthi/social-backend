package vn.nht.social.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.nht.social.Config.JwtProvider;
import vn.nht.social.Model.User;
import vn.nht.social.Repository.UserRepository;
import vn.nht.social.Request.LoginRequest;
import vn.nht.social.Service.CustomerUserDetailService;
import vn.nht.social.response.AuthRespone;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    CustomerUserDetailService customerUserDetailService;
    @PostMapping("/signup")
    public AuthRespone signup(@RequestBody User user) throws Exception {
        User isExist = userRepository.findByEmail(user.getEmail());
        if(isExist != null){
            throw  new Exception("Email already used with another account");
        }
        User userNew = new User();
        userNew.setEmail(user.getEmail());
        userNew.setPassword(passwordEncoder.encode(user.getPassword()));
        userNew.setFirstName(user.getFirstName());
        userNew.setLastName(user.getLastName());
        userNew.setGender(user.getGender());
        User userSaved = userRepository.save(userNew);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved.getEmail(),userSaved.getPassword());
        String token = JwtProvider.generateToken(authentication);

        AuthRespone res = new AuthRespone(token,"Regiser Success");
        return res;
    }

    @PostMapping("/signin")
    public AuthRespone signin(@RequestBody LoginRequest loginRequest) throws Exception {

        Authentication authentication  = authenticate(loginRequest.getEmail(),loginRequest.getPassword());
        String token = JwtProvider.generateToken(authentication);
        AuthRespone res = new AuthRespone(token,"Login Success");
    return res;
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customerUserDetailService.loadUserByUsername(email);
        if(userDetails == null){
            throw new BadCredentialsException("Invalid username");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("password not matched");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
