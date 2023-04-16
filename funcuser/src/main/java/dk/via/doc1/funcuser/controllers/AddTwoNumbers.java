package dk.via.doc1.funcuser.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;

@RestController
public class AddTwoNumbers {
    @RequestMapping(value = "addtwonumbers" ,method = RequestMethod.GET)
    public String addTwoNumbers() throws IOException {
        StringWriter result = new StringWriter();
        result.write("<h1>Serverless demo</h1>");
        Date now = new Date();
        result.write("<p>"+now.toString()+"</p></br>\n");

        final String serviceUrl = "https://function-hello-world-rkjb4zwi6a-lz.a.run.app";
        final ObjectMapper objectMapper = new ObjectMapper();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>("{\"val1\":22,\"val2\":77}", headers);

        String addResultAsJsonStr =
                restTemplate.postForObject(serviceUrl, request, String.class);
        JsonNode root = objectMapper.readTree(addResultAsJsonStr);
        if (!root.path("result").isNumber()) {
            result.write("Returned result has wrong format: " + addResultAsJsonStr + "</br>");
        } else
            result.write("The result is: "+ root.path("result").asInt());

        return result.toString();
    }
}

