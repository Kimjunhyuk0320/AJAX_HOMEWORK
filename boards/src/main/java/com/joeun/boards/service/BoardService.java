package com.joeun.boards.service;

import java.util.List;

import com.joeun.boards.dto.Board;
import com.joeun.boards.dto.Option;
import com.joeun.boards.dto.Page;

public interface BoardService {

    // 게시글 목록 조회
    public List<Board> list();

    // 게시글 읽기
    public Board select(int boardNo);

    // 게시글 쓰기
    public int insert(Board board);

    // 게시글 수정
    public int update(Board board);
    
    // 게시글 삭제
    public int delete(int boardNo);

    // 게시글 좋아요
    public int like(int boardNo);

	// 검색
	public List<Board> list(String keyword) throws Exception;
	
	// [페이지] 게시글 목록
	public List<Board> list(Page page) throws Exception;
	
	// 게시글 개수
	public int count() throws Exception;
	
	// [검색][페이지] 게시글 목록
	public List<Board> list(Page page, String keyword) throws Exception;

	// [검색+옵션][페이지] 게시글 목록
	public List<Board> list(Page page, Option option)  throws Exception;
	
	
}
