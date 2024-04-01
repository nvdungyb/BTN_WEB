package com.dzungyb.btn_web.controller;

import com.dzungyb.btn_web.model.Term;
import com.dzungyb.btn_web.repository.TermRepository;
import com.dzungyb.btn_web.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class TermController {
    @Autowired
    TermService termService;
    @GetMapping("/")
    public ModelAndView getAllTerm(){
        List<Term> list = termService.getListTerm();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Dashboard admin");
        mv.addObject("term_list",list);
        return mv;
    }

    @PostMapping("/save-term")
    public String saveTerm(@RequestParam int id, @RequestParam String ten, @RequestParam String ngay) {
        // Tạo đối tượng Term và lưu vào cơ sở dữ liệu
        Term term = new Term();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        System.out.println(id+" "+ten+" "+ngay);
        term.setId(id);
        term.setTen(ten);
        term.setNgay(ngay);
        System.out.println(term);

        termService.saveTerm(term);
        List<Term> termList = termService.getListTerm();
        for(Term term1: termList){
            System.out.println("Term: "+term1);
        }

        return "index";
    }

}
