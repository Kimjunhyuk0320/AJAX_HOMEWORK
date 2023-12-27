package com.joeun.boards.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Board {

  private int boardNo;
  private String title;  
  private String writer;  
  private String content;  
  private Date regDate;  
  private Date updDate;  
  private int views;  
  private int likes;  
  
}
