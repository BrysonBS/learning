package cn.tacos.tacocloud.security;

import cn.tacos.tacocloud.service.security.UserRepositoryUserDetailsService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/home")
                .hasRole("USER")
                .and()
                .formLogin() //使用表单校验
                .loginPage("/login") //自定义登录页面
                .loginProcessingUrl("/loginUrl") //设置登录页面请求的url路径(默认为/login)
                .usernameParameter("name")//自定义登录页面用户名参数名:默认为username
                .passwordParameter("pwd") //自定义登录页面密码参数名: 默认为password
                .defaultSuccessUrl("/success",true)//定义验证成功时跳转url地址,true表示强制跳转
                .and()
                .logout() //登出配置: 同上(默认/logout请求)
                .logoutSuccessUrl("/") //登出成功跳转页面
                .and()
                .csrf()//CSRF配置
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());//token保存在cookie中,并且设置js可读

        http.authorizeRequests()
                .antMatchers("/h2-console/**")
                .permitAll()
                .and()
                .csrf()
                .ignoringAntMatchers("/h2-console/**","/api/**")
                .and()
                .headers()
                .frameOptions()
                .sameOrigin();

        //http.csrf().ignoringAntMatchers("api/**");
    }

    private void httpSpEL(HttpSecurity http) throws Exception {
        // "/home"请求验证: 拥有ROLE_USER权限并且日期是周二才能通过
        http.authorizeRequests()
                .antMatchers("/home")
                .access("hasRole('USER') && "+
                        "T(java.time.LocalDate).now().getDayOfWeek().getValue() == "+
                        "T(java.time.DayOfWeek).TUESDAY"
                )
                .and()
                .formLogin();
    }
    private void httpAccess(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/home","/design")
                .hasRole("USER") //  /home 与 /design请求需验证"ROLE_USER"权限
                .antMatchers("/","/**").permitAll() //其他所有请求无需验证身份
                .and()
                .formLogin(); //使用表单校验
        //.loginPage("/login") 不指定或者不存在时使用Spring默认提供的/login页面
        //.and()
        //.httpBasic(); //验证方式httpBasic验证
    }

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
    @Qualifier("userRepositoryUserDetailsService") //指定具体哪一个对象@Service对应名称
    @Autowired
    private UserDetailsService userDetailsService;
    //自定义配置
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        //configureInMemory(auth);//基于内存用户存储
        //configureJdbcBased(auth); //基于jdbc用户用户存储
        //configureLDAP(auth);

        /** Customizing user authentication **/
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }
    /** In-memory user store **/
    private void configureInMemory(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder) //加密方式
                .withUser("Tom") //用户名
                .password(passwordEncoder.encode("123")) //密码
                .authorities("ROLE_USER") //权限
                .and()
                .withUser("Jerry")
                .password(passwordEncoder.encode("456"))
                .authorities("ROLE_USER");
    }
    /** JDBC-based user store **/
    //@Autowired
    private DataSource dataSource;
    private void configureJdbcBased(AuthenticationManagerBuilder auth) throws Exception{
        String USER_QUERY = "SELECT USERNAME,PASSWORD,ENABLED FROM TEST_USERS WHERE USERNAME = ?";
        String AUTHORITY_QUERY = "SELECT USERNAME,AUTHORITY FROM TEST_AUTHORITIES WHERE USERNAME = ?";
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(USER_QUERY) //自定义用户查询
                .authoritiesByUsernameQuery(AUTHORITY_QUERY) //自定义用户权限查询
                .passwordEncoder(passwordEncoder); //设置加密方式
    }
    private void configureLDAP(AuthenticationManagerBuilder auth) throws Exception{
        auth.ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                //.userSearchBase("ou=people")
                //.userSearchFilter("(uid={0})")
                .groupSearchBase("ou=groups")
                .contextSource()
                //.root("dc=tacocloud,dc=com")
                //.ldif("classpath:users.ldif")
                .url("ldap://localhost:33389/dc=tacocloud,dc=com")
                .and()
                .passwordCompare()
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .passwordAttribute("userPassword");
    }
}
