package com.dzungyb.btn_web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContestDto {
    private int idUser;
    private int idExam;
    private float score;
    private LocalDateTime startTime;
    private LocalDateTime submitTime;
    private Integer rightAnswer;
    private Integer wrongAnswer;
    private Integer blankAnswer;
}