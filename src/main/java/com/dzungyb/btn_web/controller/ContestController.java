package com.dzungyb.btn_web.controller;

import com.dzungyb.btn_web.entity.Contest;
import com.dzungyb.btn_web.model.ContestDetailModel;
import com.dzungyb.btn_web.model.dto.ContestDto;
import com.dzungyb.btn_web.service.ContestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/contests")
@RequiredArgsConstructor
public class ContestController {
    private final ContestService contestService;

    @GetMapping("/list")
    public @ResponseBody List<Contest> showList() {
        List<Contest> userList = contestService.getAll();
        String message = "Success";
        return userList;
    }

    @GetMapping("/searchById")
    public @ResponseBody Contest SearchById(@RequestParam(name = "id", required = false, defaultValue = "") int id) {
        Contest contest = contestService.searchById(id);
        return contest;
    }

    @GetMapping("/searchByIdUser")
    public @ResponseBody List<ContestDetailModel> searchByIdUser(@RequestParam(name = "id", required = false, defaultValue = "") int id) {
        List<ContestDetailModel> contestDetailModels = contestService.searchDetailByIdUser(id);
        return contestDetailModels;
    }

    @PostMapping("/create")
    public @ResponseBody ContestDto create(@RequestBody ContestDto newContest) {
        ContestDto contest = contestService.create(newContest);
        return contest;
    }

}
