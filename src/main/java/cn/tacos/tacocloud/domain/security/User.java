package cn.tacos.tacocloud.domain.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

//域对象需实现UserDetails
@Data
@Entity
@Table(name = "TEST_USERS")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private Boolean enabled;

    //返回授予用户权限集合,权限必须"ROLE_XXX"格式
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }
    //账号是否过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //是否被锁
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //凭证是否过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //用户是否启用
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
