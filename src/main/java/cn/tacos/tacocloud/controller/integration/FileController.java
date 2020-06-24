package cn.tacos.tacocloud.controller.integration;

import cn.tacos.tacocloud.integration.flows.FileWriterGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileWriterGateway fileWriterGateway;
    @GetMapping
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public String show(){
        //fileWriterGateway.writeToFile("abc.txt","1234567890");
        //System.out.println(1111);
        return "/flows/fileUpload";
    }
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void process(MultipartFile file) throws IOException {
        System.out.println(file.getOriginalFilename());
        //System.out.println(new String(file.getBytes()));
        fileWriterGateway.writeToFile(file.getOriginalFilename(),file.getBytes());
    }
}
