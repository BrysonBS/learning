package cn.tacos.tacocloud.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Controller;

import javax.swing.text.FlowView;
import java.io.File;
import java.util.concurrent.Flow;

//@Configuration
//@ImportResource("classpath:/xml/fileWriter-config.xml")
public class IntegrationConfiguration {

    /***** 方式二: Java Base配置 ******/
    //@Bean
    //@Transformer(inputChannel = "textInChannel",outputChannel = "fileWriterChannel")
    public GenericTransformer<Object,Object> upperCaseTransformer(){
        return data -> data instanceof String ? ((String) data).toUpperCase() : data;
    }
    //@Bean
    //@ServiceActivator(inputChannel = "fileWriterChannel")
    public FileWritingMessageHandler fileWriter(){
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("./temp/files"));
        handler.setExpectReply(false);//指定不需要回复消息
        handler.setFileExistsMode(FileExistsMode.APPEND);//已经存在则添加
        handler.setAppendNewLine(true);//从新的一行还是追加
        return handler;
    }

    /**** 创建MessageChannel可省略,spring会自动创建 *****/
    //@Bean
/*    public MessageChannel textInChannel(){
        return new DirectChannel();
    }*/
    //@Bean
    public MessageChannel fileWriterChannel(){
        return new DirectChannel();
    }


    /*** 方式三: Spring Integration's DSL configuration ***/
    //@Bean
    public IntegrationFlow fileWriterFlow(){
        return IntegrationFlows.from(MessageChannels.direct("textInChannel"))
                .<Object,Object>transform(data -> data instanceof String ? ((String) data).toUpperCase() : data)
                //.channel(MessageChannels.direct("fileWriterChannel")) 可省略
                .handle(Files
                        .outboundAdapter(new File("./temp/files"))
                        .fileExistsMode(FileExistsMode.APPEND)
                        .appendNewLine(true))
                .get();
    }

/*    //@Bean
    public MessageChannel textInChannel(){
        return new PublishSubscribeChannel();
    }*/
    //@Bean
    public MessageChannel fileWriterChannelA(){
        return new DirectChannel();
    }
    //@Bean
    public IntegrationFlow subscribeFlow(){
        //创建textInChannel并根据此生成flow,并发布到不同的订阅者
        return IntegrationFlows.from(MessageChannels.publishSubscribe("textInChannel"))
                .transform(data -> data instanceof String ? ((String) data).toUpperCase() : data)
                .publishSubscribeChannel(publishSubscribeSpec -> publishSubscribeSpec
                        .subscribe(flow -> flow //发布给订阅者A
                                .channel(MessageChannels.direct("A")) //使用DirectChannel: A
                                .handle(Files   //保存到文件夹temp/files/A中
                                        .outboundAdapter(new File("./temp/files/A"))
                                        .fileExistsMode(FileExistsMode.APPEND)
                                        .appendNewLine(true))
                        )
                        .subscribe(flow -> flow //发布给订阅者B
                                .channel(MessageChannels.direct("B")) //使用DirectChannel: B
                                .handle(Files   //保存到文件夹temp/files/B中
                                        .outboundAdapter(new File("./temp/files/B"))
                                        .fileExistsMode(FileExistsMode.APPEND)
                                        .appendNewLine(true))
                        )
                )
                .get();
    }

    //@Bean
    public IntegrationFlow queueFlow(){
        return IntegrationFlows.from(MessageChannels.queue("textInChannel"))
                .transform(data -> data instanceof String ? ((String) data).toUpperCase() : data)
                .bridge(bridgeHandlerGenericEndpointSpec -> bridgeHandlerGenericEndpointSpec
                        .poller(pollerFactory -> pollerFactory
                                .fixedRate(1000)
                                //.fixedDelay(1000)
                                .maxMessagesPerPoll(1)
                        )
                )
                .channel(MessageChannels.queue("writer"))
                .handle(Files   //保存到文件夹temp/files中
                        .outboundAdapter(new File("./temp/files"))
                        .fileExistsMode(FileExistsMode.APPEND)
                        .appendNewLine(true))
                .get();
    }
}
