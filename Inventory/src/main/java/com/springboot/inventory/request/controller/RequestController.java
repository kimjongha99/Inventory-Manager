package com.springboot.inventory.request.controller;

import com.springboot.inventory.request.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class RequestController {

    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping(value = "/user-request")
    public String requestPage() {
        return "requests/RequestPage";
    }

    @GetMapping(value = "/admin-main")
    public String requestListPage(Model model) {

        int repairCount = requestService.getRequestUnhandled("repair").getData().size();

        int rentalCount = requestService.getRequestUnhandled("rental").getData().size();

        int returnCount = requestService.getRequestUnhandled("return").getData().size();

        int purchaseCount = requestService.getRequestUnhandled("purchase").getData().size();

        model.addAttribute("repair", repairCount);
        model.addAttribute("rental", rentalCount);
        model.addAttribute("return", returnCount);
        model.addAttribute("purchase", purchaseCount);

        return "/requests/AdminMain";
    }

    @GetMapping(value = "/admin-requestlist")
    public String requestInfoPage (@RequestParam(name = "pageType", defaultValue = "/") String pageType,
                                   Model model) {

        ArrayList<?> requestlist = requestService.getRequestUnhandled(pageType).getData();

        model.addAttribute("requestList", requestlist);

        return "/requests/RequestListPage";
    }




}
