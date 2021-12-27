package me.seyoung.demobootweb;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class SampleController {

    /*@GetMapping("/hello")
    public String hello(){
        return "hello";
    }*/

    /*@GetMapping("/hello/{name}")
    public String hello(@PathVariable String name){ //@PathVariable : url에 파라미터를 값을 받아준다.
        return "hello " + name;
    }*/


    /*
    *
    * @PathVariable을 이용하여 name의 값을 받아 올수 있다.
    * 하지만 Person객체 안에 name으로는 받을수가 없어서 Formatter를 이용해야 한다.
    * PersonFormatter클래스 참조.
    *
    * */

    /*@GetMapping("/hello/{name}")
    public String helloFormatter(@PathVariable("name") Person person){
        return "hello " + person.getName();
    }*/

    @GetMapping("/hello")
    public String helloRequestParam(@RequestParam("id") Person person, HttpServletRequest req, HttpServletResponse rsp) {
        return "hello " + person.getName();
    }


    /********   HandlerInterceptor   *********/

    //!!! HandlerInterceptor와 Filter의 비교
    // - HandlerInterceptor는 파라미터로 Handler에 대한 정보를 받을수 있어 Spring Mvc 정보에 관련된 로직을 구현해야 할떄 사용.
    // - Filter는 Spring Mvc와 관련이 없고 일반적인 로직을 구현해야 할때 사용.

    //preHandler 1(request, response, handler) - return true : 다음 요청으로 넘긴다. / return false : 다음 요청으로 넘어가지 않는다.
    /*
      핸들러 실행 전에 호출됨
      핸들러 정보를 사용 할수 있기 때문에 서블릿 필터에 비해 세밀한 로직 구현 가능
      리턴값으로 다음 인터셉터 또는 핸드러로 요청을 전달할지(true) 끝낼지(false) 알린다.
     */
    //preHandler 2
    //요청 처리
    //비동기 요청일 경우에는 postHandler와 afterCompletion이 호출되지않는다.
    //postHandler2(request, reponse, modelAndView)
    /*
      핸들러 실행이 끝나고 뷰 랜더링 전에 실행됨
      뷰에 전달할 추가적이거나 여러 핸들러에 공통적인 모델 정보를 담는데 사용 가능하다.(modelandview)
      prehandler의 역순으로 실행된다.
    */

    //postHandler1
    //뷰 렌더링
    //afterCompletion2(request,response,handler,ex)
    /*
    * 요청 처리가 완전히 끝난 뒤에 호출됨
    * preHanlder에서 true를 리턴한 경우에만 호출됨
    * 이 메소드는 인터셉어 역순으로 호출된다.
    */
    //afterCompletion1


    //***************************** messageConverter
    @GetMapping("/message")
    //@ResponseBody - @RestController를 사용할경우에는 default로 ResponseBody 사용
    public String message(@RequestBody Person person){
        return " hello person";
    }

}
