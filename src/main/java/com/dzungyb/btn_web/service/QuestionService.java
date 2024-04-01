package com.dzungyb.btn_web.service;

import com.dzungyb.btn_web.entity.Exam;
import com.dzungyb.btn_web.entity.Question;
import com.dzungyb.btn_web.model.dto.QuestionDto;
import com.dzungyb.btn_web.repository.ExamRepository;
import com.dzungyb.btn_web.repository.QuestionRepository;
import com.dzungyb.btn_web.utils.Valids;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionService {
    private final ExamRepository examRep;
    private final QuestionRepository questionRep;

    private Question ToQuestionEntity(QuestionDto questionDto) {
        return Question.create(
                0,
                questionDto.getIdExam(),
                questionDto.getContent(),
                questionDto.getAnswer1(),
                questionDto.getAnswer2(),
                questionDto.getAnswer3(),
                questionDto.getAnswer4(),
                questionDto.getRightAnswer(),
                questionDto.getExplain()
        );
    }

    public List<Question> getAll() {
        return questionRep.findAll();
    }

    public Question searchById(int id) {
        Question question = questionRep.findFirstById(id);
        if (question == null) throw new RuntimeException("Question is not exist!");
        return question;
    }

    public void create(QuestionDto newQuestion) {
        if (Valids.isEmpty(newQuestion.getIdExam())) throw new RuntimeException("Id Exam trong");
        if (Valids.isEmpty(newQuestion.getContent())) throw new RuntimeException("Cau hoi trong");
        if (Valids.isEmpty(newQuestion.getAnswer1())) throw new RuntimeException("Dap an 1 trong");
        if (Valids.isEmpty(newQuestion.getAnswer2())) throw new RuntimeException("Dap an 2 trong");
        if (Valids.isEmpty(newQuestion.getAnswer3())) throw new RuntimeException("Dap an 3 trong");
        if (Valids.isEmpty(newQuestion.getAnswer4())) throw new RuntimeException("Dap an 4 trong");
        if (Valids.isEmpty(newQuestion.getRightAnswer())) throw new RuntimeException("Dap an dung trong");
        Exam exam = examRep.findFirstById(newQuestion.getIdExam());
        if (exam == null) throw new RuntimeException("Khong ton tai Exam co ID " + newQuestion.getIdExam());
        questionRep.save(ToQuestionEntity(newQuestion));
    }

    public void edit(Question newQuestion) {
        if (Valids.isEmpty(newQuestion.getId())) throw new RuntimeException("Something Went Worng");
        Question question = questionRep.findFirstById(newQuestion.getId());
        if (question == null) throw new RuntimeException("Something Went Worng");

        if (!Valids.isEmpty(newQuestion.getIdExam())) {
            Exam exam = examRep.findFirstById(newQuestion.getIdExam());
            if (exam == null) throw new RuntimeException("Khong ton tai Exam co ID " + newQuestion.getIdExam());
            question.setIdExam(newQuestion.getIdExam());
        }
        if (!Valids.isEmpty(newQuestion.getContent())) question.setContent(newQuestion.getContent());
        if (!Valids.isEmpty(newQuestion.getAnswer1())) question.setAnswer1(newQuestion.getAnswer1());
        if (!Valids.isEmpty(newQuestion.getAnswer2())) question.setAnswer2(newQuestion.getAnswer2());
        if (!Valids.isEmpty(newQuestion.getAnswer3())) question.setAnswer3(newQuestion.getAnswer3());
        if (!Valids.isEmpty(newQuestion.getAnswer4())) question.setAnswer4(newQuestion.getAnswer4());
        if (!Valids.isEmpty(newQuestion.getRightAnswer())) question.setRightAnswer(newQuestion.getIdExam());
        if (!Valids.isEmpty(newQuestion.getExplain())) question.setExplain(newQuestion.getExplain());

        questionRep.save(question);
    }

    public void delete(int id) {
        if (!questionRep.existsById(id)) throw new RuntimeException("Khong tim thay du lieu phu hop");
        questionRep.deleteById(id);
    }
}
