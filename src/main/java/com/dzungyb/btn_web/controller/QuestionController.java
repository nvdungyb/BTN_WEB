package com.examBE.BackendExamSys.controllers;

import com.examBE.BackendExamSys.entities.QuestionEntity;
import com.examBE.BackendExamSys.models.ApiResult;
import com.examBE.BackendExamSys.models.Dto.QuestionDto;
import com.examBE.BackendExamSys.services.QuestionService;
import com.examBE.BackendExamSys.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/questions")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/list")
    public ResponseEntity<ApiResult<List<QuestionEntity>>> showList() {
        List<QuestionEntity> userList = questionService.getAll();
        String message = "Success";
        if (userList.isEmpty()) {
            message = "Empty";
        }
        ApiResult<List<QuestionEntity>> result = ApiResult.create(HttpStatus.OK, message, userList);
        return ResponseEntity.ok(result);
    }
    @PostMapping(path = "/create")
    public ResponseEntity<ApiResult<QuestionDto>> create(@Valid @RequestBody QuestionDto questionDto) {
        ApiResult<QuestionDto> result = null;
        try{
            questionService.create(questionDto);
            result = ApiResult.create(HttpStatus.OK, Constants.SAVE_DATA_SUCCESS, questionDto);
        }
        catch (Exception ex){
            result = ApiResult.create(HttpStatus.CONFLICT, ex.getMessage(), null);
        }
        return ResponseEntity.ok(result);
    }
    @PutMapping(path = "/update")
    public ResponseEntity<ApiResult<QuestionEntity>> edit(@Valid @RequestBody QuestionEntity questionEntity) {
        ApiResult<QuestionEntity> result = null;
        try{
            questionService.edit(questionEntity);
            result = ApiResult.create(HttpStatus.OK, Constants.EDIT_DATA_SUCCESS, questionEntity);
        }
        catch (Exception ex){
            result = ApiResult.create(HttpStatus.CONFLICT, ex.getMessage(), null);
        }
        return ResponseEntity.ok(result);
    }
    @DeleteMapping(path = "/delete")
    public ResponseEntity<ApiResult<Integer>> delete(@RequestParam(name = "id", required = false, defaultValue = "") int id) {
        ApiResult<Integer> result = null;
        try{
            questionService.delete(id);
            result = ApiResult.create(HttpStatus.OK, Constants.EDIT_DATA_SUCCESS, id);
        }
        catch (Exception ex){
            result = ApiResult.create(HttpStatus.CONFLICT, ex.getMessage(), id);
        }
        return ResponseEntity.ok(result);
    }
}
