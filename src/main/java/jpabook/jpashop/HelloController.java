package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    //tymeleaf 템플릿 엔진 사용: 스프링부트가 자동으로 렌더링
    @GetMapping("hello") // localhost:8080/hello
    public String hello(Model model) {
        model.addAttribute("data", "hello!");
        return "hello"; // 스프링부트가 자동으로 resources/templates/hello.html을 찾아서 렌더링
    }
}
