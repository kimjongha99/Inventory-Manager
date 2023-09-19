package com.springboot.inventory.request.controller;

import com.springboot.inventory.common.entity.Request;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.RequestTypeEnum;
import com.springboot.inventory.common.userDetails.CustomUserDetails;
import com.springboot.inventory.request.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class RequestController {

    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    /* ========================================================================= */
    /* ================================== USER ================================= */
    /* ========================================================================= */

    // 전체요청내역
    @GetMapping("/request-user/history")
    public  String RequestDetail(Model model,
                                 @AuthenticationPrincipal CustomUserDetails userDetails) {


        List<Request> requestHistory =
                requestService.getUserRequestHistory(userDetails.getUser()).getData();

        model.addAttribute("requestHistory", requestHistory);


        return "requests/RequestDetails";
    }

    // 대여요청
    @GetMapping("/request-user/rental")
    public  String RequestSupplies() {
        return "requests/RequestSupplies";
    }

    // 반납요청
    @GetMapping("/request-user/return")
    public  String ReturnRequest(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 Model model) {

        User user = userDetails.getUser();

        List<Request> rentalRequestList = requestService.getRentalSupplyByUser(user).getData();

        model.addAttribute("rentalRequestList", rentalRequestList);

        return "requests/ReturnRequest";
    }


    /* ========================================================================= */
    /* ================================= ADMIN ================================= */
    /* ========================================================================= */

    @GetMapping(value = "/admin-main")
    public String requestListPage(Model model) {

        int rentalCount = requestService.getRequestUnhandled(RequestTypeEnum.RENTAL).getData().size();

        int returnCount =
                requestService.getRequestUnhandled(RequestTypeEnum.RETURN).getData().size();

        model.addAttribute("rental", rentalCount);
        model.addAttribute("return", returnCount);


        return "requests/AdminMainPage";
    }

    @GetMapping(value = "/admin-requestlist/rental")
    public String rentalInfoPage (Model model) {

        ArrayList<?> requestList = requestService.getRequestUnhandled(RequestTypeEnum.RENTAL).getData();

        model.addAttribute("requestList", requestList);

        return "requests/RentalRequestListPage";
    }

    @GetMapping(value = "/admin-requestlist/return")
    public String returnInfoPage (Model model) {

        ArrayList<?> requestList = requestService.getRequestUnhandled(RequestTypeEnum.RETURN).getData();

        model.addAttribute("requestList", requestList);

        return "requests/ReturnRequestListPage";
    }

    @GetMapping(value = "/admin-request-accept/rental")
    public String rentalRequestAcceptPage(@RequestParam(name = "requestId", defaultValue = "") String requestId
            , Model model) {
        
        System.out.println("컨트롤러 진입");

        Map<String, ?> response =  requestService.getRentalRequestInfo(requestId).getData();

        System.out.println(response.get("supplyList"));

        model.addAttribute("requestId", response.get("requestId"));
        model.addAttribute("supplyList", response.get("supplyList"));

        return "/requests/RentalAcceptPage";
    }



}
