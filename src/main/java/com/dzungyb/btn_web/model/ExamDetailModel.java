package com.examBE.BackendExamSys.models;

import com.examBE.BackendExamSys.entities.ExamEntity;
import com.examBE.BackendExamSys.entities.QuestionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.geom.QuadCurve2D;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDetailModel {
    private int id;
    private String name;
    private String description;
    private Boolean type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int timeDuration;
    List<QuestionEntity> question;
    public ExamDetailModel(ExamEntity exam, List<QuestionEntity> question){
        this.id = exam.getId();
        this.name = exam.getName();
        this.description = exam.getDescription();
        this.type = exam.getType();
        this.startTime = exam.getStartTime();
        this.endTime = exam.getEndTime();
        this.timeDuration = exam.getTimeDuration();
        this.question = question;
    }
}
