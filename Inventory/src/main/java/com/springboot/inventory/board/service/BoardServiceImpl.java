package com.springboot.inventory.board.service;


import com.springboot.inventory.board.dto.BoardDTO;
import com.springboot.inventory.board.dto.BoardPreviewDTO;
import com.springboot.inventory.board.dto.PageRequestDTO;
import com.springboot.inventory.board.dto.PageResponseDTO;
import com.springboot.inventory.board.repository.BoardRepository;
import com.springboot.inventory.common.entity.Board;
import com.springboot.inventory.common.enums.BoardType;
import com.springboot.inventory.common.enums.PostStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Long register(BoardType boardType, BoardDTO boardDTO) {

        Board board = new Board();
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setWriter(boardDTO.getWriter());
        board.changeStatus(PostStatus.PENDING);
        board.setBoardType(boardType);

        // If isNotice is null, set it to false
        if (boardDTO.getIsNotice() == null) {
            board.setIsNotice(false);
        } else {
            board.setIsNotice(boardDTO.getIsNotice());
        }

        Long bno = boardRepository.save(board).getBno();

        return bno;
    }

    @Override
    public BoardDTO readOne(BoardType boardType, Long bno) {

        // Find by both id and type.
        Optional<Board> result = boardRepository.findByIdAndBoardType(bno, boardType);

        Board board = result.orElseThrow(() -> new IllegalArgumentException("No such post exists."));

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


    @Override
    public List<BoardDTO> listNotices() {
        List<Board> result = boardRepository.findByIsNoticeTrue();

        List<BoardDTO> dtoList = result.stream()
                .map(board -> {
                    // 직접 엔티티를 DTO로 변환합니다.
                    BoardDTO dto = new BoardDTO();
                    dto.setBno(board.getBno());
                    dto.setTitle(board.getTitle());
                    dto.setContent(board.getContent());  // 게시글 내용 설정
                    dto.setWriter(board.getWriter());  // 작성자 설정
                    dto.setCreatedAt(board.getCreatedAt());  // 등록 날짜 설정
                    dto.setModifiedAt(board.getModifiedAt());  // 수정 날짜 설정

                    return dto;
                })
                .collect(Collectors.toList());

        return dtoList;
    }

    @Override
    public List<BoardPreviewDTO> getTop10Boards() {
        return boardRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC,"bno")))
                .stream()
                .map(board -> new BoardPreviewDTO(board.getTitle(), board.getWriter()))
                .collect(Collectors.toList());
    }
}
