package me.seyoung.demobootweb;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

//Spring에서는 Formatter는 WebMvcConfiguer를 구현하여 addFormatter에 해당 클래스를 추가해줘야 적용됨.
//@Component //- Bean으로 등록만 해주면 Spring Boot에서는 내부적으로 Formatter에 등록을 해준다.
public class PersonFormatter implements Formatter<Person> {

    /*
    *
    * Person객체를 변환 시킬수 있는 Formatter를 구현한다.
    * parse method는 파라미터로 들어온 text를 형변환하기 위한 메소드 이다.
    *
    * */

    @Override
    public Person parse(String text, Locale locale) throws ParseException {
        Person person = new Person();
        person.setName(text);
        return person;
    }

    //print method는 값을 출력시켜준다.
    @Override
    public String print(Person object, Locale locale) {
        return object.toString();
    }
}
