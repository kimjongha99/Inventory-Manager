package com.springboot.inventory.board.controller;


import com.springboot.inventory.board.dto.BoardDTO;
import com.springboot.inventory.board.dto.PageRequestDTO;
import com.springboot.inventory.board.dto.PageResponseDTO;
import com.springboot.inventory.board.service.BoardService;
import com.springboot.inventory.common.enums.PostStatus;
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

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        List<BoardDTO> notices = boardService.listNotices();  // 공지사항 목록 가져오기

        log.info(responseDTO);
        log.info(notices);

        model.addAttribute("responseDTO", responseDTO);
        model.addAttribute("notices", notices);  // 모델에 공지사항 목록 추가

    }


    @GetMapping("/register")
    public void registerGET() {

    }


    @PostMapping("/register")
    public String registerPost(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        log.info("board POST register.......");

        if (bindingResult.hasErrors()) {
            log.info("has errors.......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/board/register";
        }

        log.info(boardDTO);

        Long bno = boardService.register(boardDTO);

        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/board/list";
    }



    @GetMapping({"/read", "/modify"})
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model) {

        BoardDTO boardDTO = boardService.readOne(bno);

        log.info(boardDTO);

        model.addAttribute("dto", boardDTO);

    }
    @PostMapping("/modify")
    public String modify(@Valid BoardDTO boardDTO,
                         BindingResult bindingResult,
                         PageRequestDTO pageRequestDTO,
                         RedirectAttributes redirectAttributes) {

        log.info("board modify post......." + boardDTO);

        if (bindingResult.hasErrors()) {
            log.info("has errors.......");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            redirectAttributes.addAttribute("bno", boardDTO.getBno());

            return "redirect:/board/modify?" + link;
        }

        boardService.modify(boardDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("bno", boardDTO.getBno());

        return "redirect:/board/read";
    }


    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes) {

        log.info("remove post.. " + bno);

        boardService.remove(bno);

        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/board/list";

    }
    @PutMapping("/status/{bno}")
    public ResponseEntity<String> changeStatus(@PathVariable Long bno, @RequestBody String status) {
        boardService.changeStatus(bno, PostStatus.valueOf(status.replace("\"", ""))); // remove quotes from the status string before converting it to enum
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}