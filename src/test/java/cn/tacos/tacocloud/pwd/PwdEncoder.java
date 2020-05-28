package cn.tacos.tacocloud.pwd;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.io.InputStream;
import java.util.Optional;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

public class PwdEncoder {
    @Test
    public void encoder(){
/*        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwdEncoder = encoder.encode("123");
        System.out.println(pwdEncoder);*/
    }

    @Test
    public void combobox(){
        String result = null;
        //result = "abc";
        Optional<String> optionalS = Optional.ofNullable(result);
        System.out.println(optionalS.orElseGet(String::new));
    }
}
