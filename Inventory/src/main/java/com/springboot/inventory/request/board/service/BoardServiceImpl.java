package com.springboot.inventory.request.board.service;

import com.springboot.inventory.request.board.dto.BoardDTO;
import com.springboot.inventory.request.board.dto.PageRequestDTO;
import com.springboot.inventory.request.board.dto.PageResponseDTO;
import com.springboot.inventory.request.board.entity.Board;
import com.springboot.inventory.request.board.entity.PostStatus;
import com.springboot.inventory.request.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{

    private final ModelMapper modelMapper;

    private final BoardRepository boardRepository;
    private final ReplyService replyService;  // ReplyService 추가

    @Override
    public Long register(BoardDTO boardDTO) {

        Board board = new Board();
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setWriter(boardDTO.getWriter());
        board.changeStatus(PostStatus.PENDING);
        Long bno = boardRepository.save(board).getBno();

        return bno;
    }

    @Override
    public BoardDTO readOne(Long bno) {

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);

        return boardDTO;
    }

    @Override
    public void modify(BoardDTO boardDTO) {

        Optional<Board> result = boardRepository.findById(boardDTO.getBno());

        Board board = result.orElseThrow();

        board.change(boardDTO.getTitle(), boardDTO.getContent());

        boardRepository.save(board);

    }

    @Override
    public void remove(Long bno) {

        boardRepository.deleteById(bno);

    }



    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        List<BoardDTO> dtoList = result.getContent().stream()
                .map(board -> {
                    BoardDTO dto = modelMapper.map(board, BoardDTO.class);

                    Long replyCount = Long.valueOf(replyService.countRepliesByBoardId(board.getBno()));  // 댓글 수 계산
                    dto.setReplyCount(replyCount);  // 댓글 수 설정

                    return dto;
                }).collect(Collectors.toList());

        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public void changeStatus(Long bno, PostStatus status) {
            // 먼저 bno에 해당하는 Board 객체를 찾습니다.
            Board board = boardRepository.findById(bno)
                    .orElseThrow(() -> new IllegalArgumentException("No board found with bno: " + bno));

            // Board 객체의 상태를 바꿉니다.
            board.changeStatus(status);

            // 바뀐 상태를 저장합니다.
            boardRepository.save(board);
        }




}
