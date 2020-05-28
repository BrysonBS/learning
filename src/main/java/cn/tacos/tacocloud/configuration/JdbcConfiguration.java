package cn.tacos.tacocloud.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class JdbcConfiguration {
    /** java配置方式: SQL Server database **/
    //@Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://10.0.0.25;DatabaseName=BPMDB");
        dataSource.setUsername("jtbys");
        dataSource.setPassword("nabel..bys");
        return dataSource;
    }

    /** java配置方式: H2 database **/
    //@Bean
    public DataSource dataSourceH2(){
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        builder.setType(EmbeddedDatabaseType.H2).setName("test");
        return builder.build();
    }

    //@Bean
    public Converter<String, LocalDate> localDateConverter(){
        return source -> LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Bean
    public Converter<String, LocalDate> dateConverter() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        };
    }
}
