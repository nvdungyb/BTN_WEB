package com.dzungyb.btn_web.service;

import com.dzungyb.btn_web.entity.Contest;
import com.dzungyb.btn_web.entity.Exam;
import com.dzungyb.btn_web.model.ContestDetailModel;
import com.dzungyb.btn_web.model.ContestExamModel;
import com.dzungyb.btn_web.model.ContestUserModel;
import com.dzungyb.btn_web.model.dto.ContestDto;
import com.dzungyb.btn_web.repository.ContestRepository;
import com.dzungyb.btn_web.repository.ContestUserExamRepository;
import com.dzungyb.btn_web.repository.ExamRepository;
import com.dzungyb.btn_web.repository.UserRepository;
import com.dzungyb.btn_web.utils.Valids;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ContestService {
    private final ExamRepository examRep;
    private final ContestRepository contestRep;
    private final UserRepository userRep;
    private final ContestUserExamRepository contestUserExam;

    private Contest ToContestEntity(ContestDto contestDto) {
        return Contest.create(
                0,
                contestDto.getIdUser(),
                contestDto.getIdExam(),
                contestDto.getScore(),
                contestDto.getStartTime(),
                contestDto.getSubmitTime(),
                contestDto.getRightAnswer(),
                contestDto.getWrongAnswer(),
                contestDto.getBlankAnswer()
        );
    }

    public List<Contest> getAll() {
        return contestRep.findAll();
    }

    public Contest searchById(int id) {
        Contest contest = contestRep.findFirstById(id);
        if (contest == null) throw new RuntimeException("Contest is not exits!");
        return contest;
    }

    public ContestDetailModel searchDetailById(int id) {
        Contest contest = contestRep.findFirstById(id);
        if (contest == null) throw new RuntimeException("Contest is not exits!");
        Exam exam = examRep.findFirstById(contest.getIdExam());
        return new ContestDetailModel(contest, exam);
    }

    public List<ContestDetailModel> searchDetailByIdUser(int id) {
        List<Contest> contestList = contestRep.findByIdUser(id);
        if (contestList.isEmpty()) throw new RuntimeException("User have not done any test!");
        List<Exam> examList = examRep.findAll();
        List<ContestDetailModel> contestDetailList = new ArrayList<>();
        for (Contest contest : contestList) {
            for (Exam exam : examList) {
                if (contest.getIdExam() == exam.getId()) {
                    contestDetailList.add(new ContestDetailModel(contest, exam));
                    break;
                }
            }
        }
        return contestDetailList;
    }

    public List<Contest> searchByIdExam(int id) {
        List<Contest> contestList = contestRep.findByIdExam(id);
        if (contestList.isEmpty()) throw new RuntimeException("This contest has not created!");
        return contestList;
    }

    public void create(ContestDto newContest) {
        if (Valids.isEmpty(newContest.getIdExam())) throw new RuntimeException("Id Exam is empty");
        else {
            if (!examRep.existsById(newContest.getIdExam())) throw new RuntimeException("Exam is not exist");
        }
        if (Valids.isEmpty(newContest.getIdUser())) throw new RuntimeException("Id user is empty");
        else {
            if (!userRep.existsById(newContest.getIdUser())) throw new RuntimeException("User is not exist");
        }
        contestRep.save(ToContestEntity(newContest));
    }

    public void edit(Contest newContest) {
        if (Valids.isEmpty(newContest.getId())) throw new RuntimeException("Contest is null");
        Contest preContest = contestRep.findFirstById(newContest.getId());
        if (!Valids.isEmpty(newContest.getIdExam())) {
            if (!examRep.existsById(newContest.getIdExam())) throw new RuntimeException("Exam not found");
            else preContest.setIdExam(newContest.getIdExam());
        }
        if (!Valids.isEmpty(newContest.getIdUser())) {
            if (!userRep.existsById(newContest.getIdUser())) throw new RuntimeException("User not found");
            else preContest.setIdUser(newContest.getIdUser());
        }
        preContest.setScore(newContest.getScore());
        preContest.setStartTime(newContest.getStartTime());
        preContest.setSubmitTime(newContest.getSubmitTime());
        preContest.setRightAnswer(newContest.getRightAnswer());
        preContest.setRightAnswer(newContest.getRightAnswer());
        preContest.setWrongAnswer(newContest.getWrongAnswer());
        preContest.setBlankAnswer(newContest.getBlankAnswer());

        contestRep.save(preContest);
    }

    public void delete(int id) {
        if (!contestRep.existsById(id)) throw new RuntimeException("Khong tim thay du lieu phu hop");
        contestRep.deleteById(id);
    }

    public List<ContestUserModel> findWithUser() {
        List<Object[]> ls = contestUserExam.statisticByUser();
        List<ContestUserModel> rs = new ArrayList<>();
        for (Object[] c : ls) {
            rs.add(new ContestUserModel(c));
        }
        return rs;
    }

    public List<ContestExamModel> findWithExam() {
        List<Object[]> ls = contestUserExam.statisticByExam();
        List<ContestExamModel> rs = new ArrayList<>();
        for (Object[] c : ls) {
            rs.add(new ContestExamModel(c));
        }
        return rs;
    }
}
