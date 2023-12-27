package com.joeun.boards.service;

import java.util.List;

import com.joeun.boards.dto.Comment;


public interface CommentService {
  
  public List<Comment> commentList(Comment comment);

  public int commentInsert(Comment comment);

  public int commentDelete(Comment comment);

  public int commentUpdate(Comment comment);

}
