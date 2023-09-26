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
    public Long register(BoardType boardType, BoardDTO boardDto) {
        Board board = dtoToEntity(boardDto); // Convert DTO to entity

        // Set the board type and default status.
        board.setBoardType(boardType);
        board.changeStatus(PostStatus.PENDING);

        // If isNotice is null, set it to false
        if (board.getIsNotice() == null) {
            board.setIsNotice(false);
        }

        Long bno = boardRepository.save(board).getBno();

        return bno;
    }
    private Board dtoToEntity(BoardDTO dto) {
        Board entity = new Board();

        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setWriter(dto.getWriter());
        entity.setIsNotice(dto.getIsNotice());

        return entity;
    }
    @Override
    public BoardDTO readOne(BoardType boardType, Long bno) {

        // Find by both id and type.
        Optional<Board> result = boardRepository.findByBnoAndBoardType(bno, boardType);

        Board board = result.orElseThrow(() -> new IllegalArgumentException("No such post exists."));

        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);

        return boardDTO;
    }

    @Override
    public void modify(BoardType boardType, BoardDTO boardDTO) {

        // Find by both id and type.
        Optional<Board> result = boardRepository.findByBnoAndBoardType(boardDTO.getBno(), boardType);

        Board board = result.orElseThrow(() -> new IllegalArgumentException("No such post exists."));

        // Change the title and content of the found post.
        board.change(boardDTO.getTitle(), boardDTO.getContent());

        // Save the changes.
        boardRepository.save(board);
    }


    @Override
    public void remove(BoardType boardType, Long bno) {

        // Find by both id and type.
        Optional<Board> result = boardRepository.findByBnoAndBoardType(bno, boardType);

        Board board = result.orElseThrow(() -> new IllegalArgumentException("No such post exists."));

        // Delete the found post.
        boardRepository.delete(board);
    }


    @Override
    public PageResponseDTO<BoardDTO> list(BoardType boardType, PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        // Add the board type to the search parameters.
        Page<Board> result = boardRepository.searchAll(boardType, types, keyword, pageable);

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
    public void changeStatus(BoardType boardType, Long bno, PostStatus status) {
        // Find by both id and type.
        Optional<Board> result = boardRepository.findByBnoAndBoardType(bno, boardType);

        Board board = result.orElseThrow(() -> new IllegalArgumentException("No such post exists."));

        // Change the status of the found post.
        board.changeStatus(status);

        // Save the changes.
        boardRepository.save(board);
    }


    @Override
    public List<BoardDTO> listNotices(BoardType boardType) {
        List<Board> result = boardRepository.findByIsNoticeTrueAndBoardType(boardType);

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
    public List<BoardPreviewDTO> getTop10BoardsPurchase() {
        return boardRepository.findAllByBoardTypeAndStatusOrderByBnoDesc(BoardType.PURCHASE, PostStatus.PENDING, PageRequest.of(0, 10))
                .stream()
                .map(board -> new BoardPreviewDTO(board.getTitle(), board.getWriter()))
                .collect(Collectors.toList());
    }
        @Override
        public List<BoardPreviewDTO> getTop10BoardsRepair() {
            return boardRepository.findAllByBoardTypeAndStatusOrderByBnoDesc(BoardType.REPAIR, PostStatus.PENDING, PageRequest.of(0, 10))
                    .stream()
                    .map(board -> new BoardPreviewDTO(board.getTitle(), board.getWriter()))
                    .collect(Collectors.toList());
        }
}
