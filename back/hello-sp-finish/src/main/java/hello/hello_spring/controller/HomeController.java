package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";      //로컬 호스트8080 요청이 오면 먼저 여기서 찾아보고 home이 매핑된것을 확인하고 home 컨트롤러를 호출하고 index.html을 무시하게 된다.
    }
}
