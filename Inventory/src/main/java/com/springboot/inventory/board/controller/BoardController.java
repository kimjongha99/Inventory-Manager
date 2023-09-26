package com.springboot.inventory.board.controller;


import com.springboot.inventory.board.dto.BoardDTO;
import com.springboot.inventory.board.dto.PageRequestDTO;
import com.springboot.inventory.board.dto.PageResponseDTO;
import com.springboot.inventory.board.service.BoardService;
import com.springboot.inventory.common.enums.PostStatus;
import com.springboot.inventory.common.enums.BoardType;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;



@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
//    PURCHASE
//    REPAIR
    private final BoardService boardService;

    @GetMapping("/purchase/list")
    public void listPurchase(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<BoardDTO> responseDTO = boardService.list(BoardType.PURCHASE, pageRequestDTO);
        List<BoardDTO> notices = boardService.listNotices(BoardType.PURCHASE);  // Get notice list

        log.info(responseDTO);
        log.info(notices);

        model.addAttribute("responseDTO", responseDTO);
        model.addAttribute("notices", notices);  // Add notice list to the model
    }

    @GetMapping("/repair/list")
    public void listRepair(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<BoardDTO> responseDTO = boardService.list(BoardType.REPAIR, pageRequestDTO);
        List<BoardDTO> notices = boardService.listNotices(BoardType.REPAIR);  // Get notice list

        log.info(responseDTO);
        log.info(notices);

        model.addAttribute("responseDTO", responseDTO);
        model.addAttribute("notices", notices);  // Add notice list to the model
    }



    @GetMapping("/purchase/register")
    public void registerPurchaseGET() {
        // If you need to do anything specific for repair board registration, do it here.
    }

    @PostMapping("/purchase/register")
    public String registerPurchasePost(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("purchase board POST register.......");

        if (bindingResult.hasErrors()) {
            log.info("has errors.......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/board/purchase/register";
        }

        // Set the board type to PURCHASE
        boardDTO.setBoardType(BoardType.PURCHASE);

        log.info(boardDTO);

        // Pass both BoardType and BoardDTO to the register method
        Long bno = boardService.register(BoardType.PURCHASE, boardDTO);

        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/board/purchase/list";
    }

    @GetMapping("/repair/register")
    public void registerRepairGET() {
        // If you need to do anything specific for repair board registration, do it here.
    }

    @PostMapping("/repair/register")
    public String registerRepairPost(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("repair board POST register.......");

        if (bindingResult.hasErrors()) {
            log.info("has errors.......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/board/repair/register";
        }

        // Set the board type to REPAIR
        boardDTO.setBoardType(BoardType.REPAIR);

        log.info(boardDTO);

        Long bno = boardService.register(BoardType.REPAIR, boardDTO);

        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/board/repair/list";
    }



    @GetMapping({"/purchase/read", "/purchase/modify"})
    public void readPurchase(Long bno, PageRequestDTO pageRequestDTO, Model model) {
        BoardDTO boardDTO = boardService.readOne(BoardType.PURCHASE, bno);

        // Check if the board type is PURCHASE
        if (boardDTO.getBoardType() != BoardType.PURCHASE) {
            throw new IllegalArgumentException("This is not a purchase post.");
        }

        log.info(boardDTO);

        model.addAttribute("dto", boardDTO);
    }

    @GetMapping({"/repair/read", "/repair/modify"})
    public void readRepair(Long bno, PageRequestDTO pageRequestDto, Model model) {
        BoardDTO boardDTO = boardService.readOne(BoardType.REPAIR, bno);


        // Check if the board type is PURCHASE
        if (boardDTO.getBoardType() != BoardType.REPAIR) {
            throw new IllegalArgumentException("This is not a purchase post.");
        }

        log.info(boardDTO);

        model.addAttribute("dto", boardDTO);
    }


    @PostMapping("/purchase/modify")
    public String modifyPurchase(@Valid BoardDTO boardDTO,
                                 BindingResult bindingResult,
                                 PageRequestDTO pageRequestDTO,
                                 RedirectAttributes redirectAttributes) {

        log.info("purchase board modify post......." + boardDTO);

        if (bindingResult.hasErrors()) {
            log.info("has errors.......");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            redirectAttributes.addAttribute("bno", boardDTO.getBno());

            return "redirect:/board/purchase/modify?" + link;
        }

        // Pass both BoardType and BoardDTO to the modify method
        boardService.modify(BoardType.PURCHASE, boardDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("bno", boardDTO.getBno());

        return "redirect:/board/purchase/read";
    }


    @PostMapping("/repair/modify")
    public String modifyRepair(@Valid BoardDTO boardDTO,
                               BindingResult bindingResult,
                               PageRequestDTO pageRequestDTO,
                               RedirectAttributes redirectAttributes) {

        log.info("repair board modify post......." + boardDTO);

        if (bindingResult.hasErrors()) {
            log.info("has errors.......");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            redirectAttributes.addAttribute("bno", boardDTO.getBno());

            return "redirect:/board/repair/modify?" + link;
        }
        // Pass both BoardType and BoardDTO to the modify method
        boardService.modify(BoardType.REPAIR, boardDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("bno", boardDTO.getBno());

        return "redirect:/board/repair/read";

    }


        @PostMapping("/purchase/remove")
        public String removePurchase(Long bno, RedirectAttributes redirectAttributes) {

            log.info("remove purchase post.. " + bno);

            // Pass BoardType to the remove method
            boardService.remove(BoardType.PURCHASE, bno);

            redirectAttributes.addFlashAttribute("result", "removed");

            return "redirect:/board/purchase/list";

    }

    @PostMapping("/repair/remove")
    public String removeRepair(Long bno, RedirectAttributes redirectAttributes) {

        log.info("remove repair post.. " + bno);

        // Pass BoardType to the remove method
        boardService.remove(BoardType.REPAIR, bno);

        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/board/repair/list";
    }


    @PutMapping("/purchase/status/{bno}")
    public ResponseEntity<String> changePurchaseStatus(@PathVariable Long bno, @RequestBody String status) {

        // Pass both BoardType and PostStatus to the changeStatus method
        boardService.changeStatus(BoardType.PURCHASE, bno, PostStatus.valueOf(status.replace("\"", "")));

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PutMapping("/repair/status/{bno}")
    public ResponseEntity<String> changeRepairStatus(@PathVariable Long bno, @RequestBody String status) {
        boardService.changeStatus(BoardType.REPAIR, bno, PostStatus.valueOf(status.replace("\"", "")));


        return new ResponseEntity<>("success", HttpStatus.OK);


    }

}