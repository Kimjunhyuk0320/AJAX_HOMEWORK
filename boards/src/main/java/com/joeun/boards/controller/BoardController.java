package com.joeun.boards.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joeun.boards.dto.Board;
import com.joeun.boards.dto.Comment;
import com.joeun.boards.dto.Option;
import com.joeun.boards.dto.Page;
import com.joeun.boards.service.BoardService;
import com.joeun.boards.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    
    @Autowired
    private CommentService commentService;

   	// 게시글 목록
	@GetMapping("/list")
	public String list(Model model, Option option, Page page) throws Exception {
		
		log.info("##### 페이징 처리 전 - page #####");
		log.info(page.toString());
		log.info("keyword : " + option.getKeyword());
		
		// 게시글 목록 요청
		List<Board> boardList = boardService.list(page, option);
		
		log.info("##### 페이징 처리 후 - page #####");
		log.info(page.toString());
		
		// 게시글 목록 모델에 등록
		model.addAttribute("boardList", boardList);
		model.addAttribute("page", page);
		
		return "/board/list";
	}

    /**
     * 게시글 조회
     * [GET] 
     * /board/read
     * - model : board, fileList
     * @param model
     * @param boardNo
     * @return
     * @throws Exception
     */
    @GetMapping(value="/read")
    public String read(Model model, int boardNo) throws Exception {
        log.info("[GET] - /board/read");

        // 데이터 요청
        Board board = boardService.select(boardNo);     // 게시글 정보

        // 모델 등록
        model.addAttribute("board", board);
        // 뷰 페이지 지정
        return "board/read";
    }


    /**
     * 게시글 쓰기
     * [GET]
     * /board/insert
     * model : ❌ 
     * @return
     */
    @GetMapping(value="/insert")
    public String insert(@ModelAttribute Board board) {
        log.info("[GET] - /board/insert");

        return "board/insert";
    }
    
    /**
     * 게시글 쓰기 처리
     * [POST]
     * /board/insert
     * model : ❌
     * @param board
     * @return
     * @throws Exception
     */
    @PostMapping(value="/insert")
    public String insertPro(@ModelAttribute Board board) throws Exception {
           log.info("[POST] - /board/insert");
        // @ModelAttribute : 모델에 자동으로 등록해주는 어노테이션
        // 데이터 처리
        int result = boardService.insert(board);

        // 게시글 쓰기 실패 ➡ 게시글 쓰기 화면
        if( result == 0 ) return "board/insert";

        // 뷰 페이지 지정
        return "redirect:/board/list";
    }
    
    /**
     * 게시글 수정
     * [GET]
     * /board/update
     * model : board
     * @param model
     * @param boardNo
     * @return
     * @throws Exception
     */
    @GetMapping(value="/update")
    public String update(Model model, int boardNo) throws Exception {
        log.info("[GET] - /board/update");
        // 데이터 요청
        Board board = boardService.select(boardNo);
        // 모델 등록
        model.addAttribute("board", board);
        // 뷰 페이지 지정
        return "board/update";
    }
    /**
     * 게시글 수정 처리
     * [POST]
     * /board/update
     * model : board
     * @param board
     * @return
     * @throws Exception
     */
    @PostMapping(value="/update")
    public String updatePro(Board board) throws Exception {
        log.info("[POST] - /board/update");
        // 데이터 처리
        int result = boardService.update(board);
        int boardNo = board.getBoardNo();

        // 게시글 수정 실패 ➡ 게시글 수정 화면
        if( result == 0 ) return "redirect:/board/update?boardNo=" + boardNo;
        
        // 뷰 페이지 지정
        return "redirect:/board/list";
    }

    /**
     * 게시글 삭제 처리
     * [POST]
     * /board/delete
     * model : ❌
     * @param boardNo
     * @return
     * @throws Exception
     */
    @PostMapping(value="/delete")
    public String deletePro(int boardNo) throws Exception {
        log.info("[POST] - /board/delete");
        // 데이터 처리
        int result = boardService.delete(boardNo);
        
        // 게시글 삭제 실패 ➡ 게시글 수정 화면
        if( result == 0 ) return "redirect:/board/update?boardNo=" + boardNo;

        // 뷰 페이지 지정
        return "redirect:/board/list";
    }
    


      
  @ResponseBody
  @GetMapping(value="/commentList", produces = "application/json")
  public List<Comment> commentList(Comment comment) {
    comment.setParentTable("board");
    List<Comment> commentList = commentService.commentList(comment);
    
    return commentList;
  }

  @ResponseBody
  @GetMapping(value="/commentInsert")
  public String commentInsert(Comment comment) {
    comment.setParentTable("board");
    int result = commentService.commentInsert(comment);
    if(result>0){
      return "SUCCESS";
    }
    else{

      return "FAILED";
    }
  }

  @ResponseBody
  @GetMapping(value="/commentDelete")
  public String commentDelete(Comment comment) {

    comment.setParentTable("board");
    int result = commentService.commentDelete(comment);
    if(result>0){
      return "SUCCESS";
    }
    else{

      return "FAILED";
    }
  }

  @ResponseBody
  @GetMapping(value="/commentUpdate")
  public String commentUpdate(Comment comment) {

    comment.setParentTable("board");
    int result = commentService.commentUpdate(comment);
    if(result>0){
      return "SUCCESS";
    }
    else{

      return "FAILED";
    }
  }
  
  // 게시글 좋아요 + 1
  @ResponseBody
  @GetMapping(value="/like")
  public String like(Board board) {
   int boardNo = board.getBoardNo();
   int result = boardService.like(boardNo);
   if( result > 1){
     return "SUCCESS";
   }else{
    return "FAIL";
   }
  }
  

  









}
