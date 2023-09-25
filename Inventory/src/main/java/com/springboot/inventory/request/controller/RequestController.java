package com.springboot.inventory.request.controller;

import com.springboot.inventory.common.entity.Request;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.RequestTypeEnum;
import com.springboot.inventory.common.security.UserDetailsImpl;
import com.springboot.inventory.request.service.RequestService;

//
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

//
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
    public  String RequestDetail(@AuthenticationPrincipal
                                     UserDetailsImpl userDetails,
                                 @RequestParam(name = "page", defaultValue = "0") int page, Model model
                                 ) {

        Page<?> requestHistory =
                requestService.getUserRequestHistory(userDetails.getUser(), page).getData();

        model.addAttribute("requestHistory", requestHistory.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", requestHistory.getTotalPages());

        return "requests/RequestDetails";
    }

    // 대여요청
    @GetMapping("/request-user/rental")
    public  String RequestSupplies() {
        return "requests/RequestSupplies";
    }

    // 반납요청
    @GetMapping("/request-user/return")
    public  String ReturnRequest(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                 Model model) {

        User user = userDetails.getUser();

        List<Request> rentalRequestList = requestService.getRentalSupplyByUser(user).getData();

        model.addAttribute("rentalRequestList", rentalRequestList);

        return "requests/ReturnRequest";
    }


    /* ========================================================================= */
    /* ================================= ADMIN ================================= */
    /* ========================================================================= */


    // 대여 요청 목록
    @GetMapping(value = "/admin-requestlist/rental")
    public String rentalInfoPage (@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "category", defaultValue = "") String categoryName, Model model) {


           Page<?> requestList =
                   requestService.getRequestUnhandledByCategory(RequestTypeEnum.RENTAL, categoryName,
                    page).getData();


        model.addAttribute("requestList", requestList.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", requestList.getTotalPages());
        model.addAttribute("category", categoryName);

        return "requests/RentalRequestListPage";
    }

    // 반납 요청 목록
    @GetMapping(value = "/admin-requestlist/return")
    public String returnInfoPage (Model model) {

        ArrayList<?> requestList = requestService.getRequestUnhandled(RequestTypeEnum.RETURN).getData();

        model.addAttribute("requestList", requestList);

        return "requests/ReturnRequestListPage";
    }

    // 대여 요청 승인
    @GetMapping(value = "/admin-request-accept/rental")
    public String rentalRequestAcceptPage(@RequestParam(name = "requestId", defaultValue = "") String requestId
            , Model model) {
        

        Map<String, ?> response =  requestService.getRentalRequestInfo(requestId).getData();


        model.addAttribute("requestId", response.get("requestId"));
        model.addAttribute("supplyList", response.get("supplyList"));

        return "/requests/RentalAcceptPage";
    }



}
