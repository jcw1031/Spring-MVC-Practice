package hello.exception.servlet;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/test")
@Controller
public class TestController {

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView test() {
        return new ModelAndView("test");
    }
}
