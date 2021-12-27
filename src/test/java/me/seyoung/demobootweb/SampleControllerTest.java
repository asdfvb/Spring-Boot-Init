package me.seyoung.demobootweb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.oxm.Marshaller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import sun.net.www.MimeTable;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import java.io.StringWriter;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
//@WebMvcTest //Web과 관련된 빈들만 등록하여 테스트 함.(MockMvc클래스를 주입받을수 있다.) ex) @Component .. 등 일반 빈등록 어노테이션은 실행되지않아 사용 불가
@SpringBootTest //- 모든 빈들을 등록하여 테스트 함. (통합 테스트)
@AutoConfigureMockMvc //- springBootTest 어노테이션을 이용하면 프로젝트 내에 모든 빈들을 등록하지만... 테스트에 필요한 MockMvc를 빈으로 등록하지않아 객체 사용 불가
//                      - 그래서 AutoConfigureMockMvc어노테이션으로 MockMvc를 빈으로 등록해줘야 테스트에 사용 가능하다.
public class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    Marshaller marshaller;

    //get 방식의 url을 호출하여 결과 받아보기
    /*@Test
    public void Hello() throws Exception{
        this.mockMvc
                .perform(get("/hello"))//hello라는 요청을보내면
                .andDo(print())//요청과 응답을 출력해볼수 있다.
                .andExpect(content().string("hello")); //hello라는 내용이 나올 것이다.
    }

    //Formatter를 이용하여 url parameter값을 Person객체로 받아보기
    @Test
    public void HelloFriend() throws Exception{
        this.mockMvc
                .perform(get("/hello/seyoung"))
                .andDo(print())
                .andExpect(content().string("hello seyoung"));
    }*/

    //jpa를 사용하여 사람을 한명 등록한 후,
    //생성된 사람의 키값으로 /hello url 호출하여 이름 얻기
    /*@Test
    public void helloRequestParam() throws Exception {
        Person person = new Person();
        person.setName("seyoung");
        Person savedPerson = personRepository.save(person);

        this.mockMvc
                .perform(get("/hello")
                        .param("id", savedPerson.getId().toString()))
                .andDo(print())
                .andExpect(content().string("hello seyoung"));
    }*/

    @Test
    public void helloStatic() throws Exception {
        this.mockMvc.perform(get("/mobile/index.html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string((Matchers.containsString("hello mobile"))))
                .andExpect(header().exists(HttpHeaders.CACHE_CONTROL));
    }

    @Test
    public void stringMessage() throws Exception{
        this.mockMvc.perform(get("/message")
                .content("hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));


    }

    @Test
    public void jsonMessage() throws Exception{
        Person person = new Person();
        person.setId(2019l);
        person.setName("seyoung");

        String jsonstring = objectMapper.writeValueAsString(person);

        this.mockMvc.perform(get("/jsonMessage")
                        //본문에 보내는 데이터가 JSON 타입이다를 알려줌
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        //JSON으로 응답이 오길 원한다.
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(jsonstring))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2019))
                .andExpect(jsonPath("$.name").value("seyoung"));

    }

    @Test
    public void xmlMessage() throws Exception{
        Person person = new Person();
        person.setId(2019l);
        person.setName("seyoung");

        StringWriter stringWriter = new StringWriter();
        Result result = new StreamResult(stringWriter);
        marshaller.marshal(person, result);
        String xmlString = stringWriter.toString();

        this.mockMvc.perform(get("/jsonMessage")
                        //본문에 보내는 데이터가 XML 타입이다를 알려줌
                        .contentType(MediaType.APPLICATION_XML)
                        //XML으로 응답이 오길 원한다.
                        .accept(MediaType.APPLICATION_XML)
                        .content(xmlString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("person/name").string("seyoung"))
                .andExpect(xpath("person/id").string("2019"));
    }
}