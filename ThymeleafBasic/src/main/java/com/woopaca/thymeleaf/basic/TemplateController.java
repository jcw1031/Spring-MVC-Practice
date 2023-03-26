package com.woopaca.thymeleaf.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/template")
public class TemplateController {

    @GetMapping("/fragment")
    public String template() {
        return "template/fragment/fragment-main";
    }

    @GetMapping("/layout")
    public String layout() {
        return "template/layout/layout-main";
    }

    @GetMapping("/layout-extend")
    public String layoutExtend() {
        return "template/layoutExtend/layout-extend-main";
    }
}
