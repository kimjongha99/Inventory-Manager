package com.springboot.inventory.request.controller;

import com.springboot.inventory.common.entity.Request;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.RequestTypeEnum;
import com.springboot.inventory.common.userDetails.CustomUserDetails;
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
                                 CustomUserDetails userDetails,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "size", defaultValue = "10") int size,
                                 Model model
                                 ) {

        List<Request> requestHistory =
                requestService.getUserRequestHistory(userDetails.getUser()).getData();

        Page<?> historyPage = requestService.paging(page, size, requestHistory).getData();

        model.addAttribute("requestHistory", historyPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", historyPage.getTotalPages());
        model.addAttribute("currentUrl", "/request-user/history");


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
    public String rentalInfoPage (@RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "10") int size,
                                  Model model) {

        ArrayList<?> requestList = requestService.getRequestUnhandled(RequestTypeEnum.RENTAL).getData();

        Page<?> rentalReqeustPage = requestService.paging(page, size, requestList).getData();

        model.addAttribute("requestList", rentalReqeustPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", rentalReqeustPage.getTotalPages());
        model.addAttribute("currentUrl", "/admin-requestlist/rental");

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
        

        Map<String, ?> response =  requestService.getRentalRequestInfo(requestId).getData();


        model.addAttribute("requestId", response.get("requestId"));
        model.addAttribute("supplyList", response.get("supplyList"));

        return "/requests/RentalAcceptPage";
    }



}
