package com.examBE.BackendExamSys.models.Dto;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    private int idExam;
    private String content;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private Integer rightAnswer;
    private String explain;
}
