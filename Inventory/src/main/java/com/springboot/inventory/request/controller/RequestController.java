package com.springboot.inventory.request.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RequestController {

    private ModelAndView createModelAndView(String view) {
        return new ModelAndView(view);
    }

    @GetMapping(value = "/request")
    public ModelAndView requestPage() {
        return createModelAndView("requests/RequestPage");
    }


}
