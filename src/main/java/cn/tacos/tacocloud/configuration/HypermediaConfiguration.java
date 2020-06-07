package cn.tacos.tacocloud.configuration;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class HypermediaConfiguration{
    //为PopInStock对象添加额外链接(自定义的处理/recent请求的Controller注册到Spring Data REST中去)
    //使Spring Data REST自动生成的API中可以导航到自定义Controller的连接
    @Bean
    public RepresentationModelProcessor<EntityModel<PopInStock>> popInStockProcessor(EntityLinks links){
        return new RepresentationModelProcessor<EntityModel<PopInStock>>() {
            @Override
            public EntityModel<PopInStock> process(EntityModel<PopInStock> model) {
                model.add(links.linkFor(PopInStock.class).slash("recent").withRel("recents"));
                return model;
            }
        };
    }
}
