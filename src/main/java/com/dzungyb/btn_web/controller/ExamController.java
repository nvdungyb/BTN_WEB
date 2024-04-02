package com.examBE.BackendExamSys.controllers;

import com.examBE.BackendExamSys.entities.ExamEntity;
import com.examBE.BackendExamSys.models.ApiResult;
import com.examBE.BackendExamSys.models.Dto.ExamDto;
import com.examBE.BackendExamSys.models.Dto.ExamQuestionDto;
import com.examBE.BackendExamSys.models.Dto.UserDto;
import com.examBE.BackendExamSys.models.ExamDetailModel;
import com.examBE.BackendExamSys.services.ExamService;
import com.examBE.BackendExamSys.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/exams")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ExamController {
    private final ExamService examService;

    @GetMapping("/list")
    public ResponseEntity<ApiResult<List<ExamEntity>>> showList() {
        List<ExamEntity> userList = examService.getAll();
        String message = "Success";
        if (userList.isEmpty()) {
            message = "Empty";
        }
        ApiResult<List<ExamEntity>> result = ApiResult.create(HttpStatus.OK, message, userList);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/listDetail")
    public ResponseEntity<ApiResult<List<ExamDetailModel>>> showListDetail() {
        List<ExamDetailModel> userList = examService.getAllDetail();
        String message = "Success";
        if (userList.isEmpty()) {
            message = "Empty";
        }
        ApiResult<List<ExamDetailModel>> result = ApiResult.create(HttpStatus.OK, message, userList);
        return ResponseEntity.ok(result);
    }
    @GetMapping(path = "/searchById")
    public ResponseEntity<ApiResult<ExamEntity>> searchById(@RequestParam(name = "id", required = false, defaultValue = "") int id) {
        ApiResult<ExamEntity> result = null;
        try{
            ExamEntity exam = examService.searchById(id);
            result = ApiResult.create(HttpStatus.OK, Constants.SEARCH_DATA_SUCCESS, exam);
        }
        catch (Exception ex) {
            result = ApiResult.create(HttpStatus.CONFLICT, ex.getMessage(), null);
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping(path = "/searchDetailById")
    public ResponseEntity<ApiResult<ExamDetailModel>> searchDetailById(@RequestParam(name = "id", required = false, defaultValue = "") int id) {
        ApiResult<ExamDetailModel> result = null;
        try{
            ExamDetailModel exam = examService.searchDetailById(id);
            result = ApiResult.create(HttpStatus.OK, Constants.SEARCH_DATA_SUCCESS, exam);
        }
        catch (Exception ex) {
            result = ApiResult.create(HttpStatus.CONFLICT, ex.getMessage(), null);
        }
        return ResponseEntity.ok(result);
    }
    @PostMapping(path = "/create")
    public ResponseEntity<ApiResult<ExamDto>> create(@RequestBody ExamDto examDto) {
        ApiResult<ExamDto> result = null;
        try{
            examService.create(examDto);
            result = ApiResult.create(HttpStatus.OK, Constants.SAVE_DATA_SUCCESS, examDto);
        }
        catch (Exception ex) {
            result = ApiResult.create(HttpStatus.CONFLICT, ex.getMessage(), examDto);
        }
        return ResponseEntity.ok(result);
    }
    @PostMapping(path = "/createAll")
    public ResponseEntity<ApiResult<ExamQuestionDto>> createAll(@RequestBody ExamQuestionDto examQuestionDto) {
        ApiResult<ExamQuestionDto> result = null;
        try{
            examService.createAll(examQuestionDto);
            result = ApiResult.create(HttpStatus.OK, Constants.SAVE_DATA_SUCCESS, examQuestionDto);
        }
        catch (Exception ex) {
            result = ApiResult.create(HttpStatus.CONFLICT, ex.getMessage(), examQuestionDto);
        }
        return ResponseEntity.ok(result);
    }
    @PutMapping(path = "/update")
    public ResponseEntity<ApiResult<ExamEntity>> update(@RequestBody ExamEntity examEntity) {
        ApiResult<ExamEntity> result = null;
        try{
            examService.edit(examEntity);
            result = ApiResult.create(HttpStatus.OK, Constants.EDIT_DATA_SUCCESS, examEntity);
        }
        catch (Exception ex) {
            result = ApiResult.create(HttpStatus.CONFLICT, ex.getMessage(), examEntity);
        }
        return ResponseEntity.ok(result);
    }
    
    @PutMapping(path = "/updateAll")
    public ResponseEntity<ApiResult<ExamDetailModel>> updateAll(@RequestBody ExamDetailModel examDetailModel) {
        ApiResult<ExamDetailModel> result = null;
        try{
            examService.editAll(examDetailModel);
            result = ApiResult.create(HttpStatus.OK, Constants.EDIT_DATA_SUCCESS, examDetailModel);
        }
        catch (Exception ex) {
            result = ApiResult.create(HttpStatus.CONFLICT, ex.getMessage(), examDetailModel);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<ApiResult<Integer>> delete(@RequestParam(name = "id", required = false, defaultValue = "") int id){
        ApiResult<Integer> result = null;
        try{
            examService.delete(id);
            result = ApiResult.create(HttpStatus.OK, Constants.DELETE_DATA_SUCCESS, id);
        }
        catch (Exception ex) {
            result = ApiResult.create(HttpStatus.CONFLICT, ex.getMessage(), id);
        }
        return ResponseEntity.ok(result);
    }
}
