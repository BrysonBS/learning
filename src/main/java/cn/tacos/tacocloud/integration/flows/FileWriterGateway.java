package cn.tacos.tacocloud.integration.flows;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * 创建一个Integration flow
 * 入口端点: textInChannel
 * 出口端点(endpoint): 需要通过配置指定
 * 两个方法: 将data数据放入到入口textInChannel中
 */
@MessagingGateway(defaultRequestChannel = "textInChannel")
public interface FileWriterGateway {
    void writeToFile(@Header(FileHeaders.FILENAME) String filename, String data);
    void writeToFile(@Header(FileHeaders.FILENAME) String filename,byte[] bytes);
}
