package com.springboot.inventory.common;


import org.hibernate.validator.constraints.br.CPF;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("main")
public class AllPageController {

    @GetMapping("/dashboard")
    public  String maindashboard() {
        return "dashboard/maindashboard";
    }


    @GetMapping("/request-detail")   // 전체요청내역
    public  String RequestDetail() {
        return "dashboard/requestinformation/RequestDetails";
    }

    @GetMapping("/buy-request")   // 구매요청
    public  String BuyRequest() {
        return "dashboard/section/BuyRequest";
    }


    @GetMapping("/repair-request")   // 수리요청
    public  String RepairRequest() {
        return "dashboard/section/RequestSupplies";
    }

    @GetMapping("/request-supplies")   // 비품
    public  String RequestSupplies() {
        return "dashboard/section/RequestSupplies";
    }

    @GetMapping("/return-request")   // 반납요청
    public  String ReturnRequest() {
        return "dashboard/section/ReturnRequest";
    }

}
