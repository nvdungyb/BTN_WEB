package com.dzungyb.btn_web.service;

import com.dzungyb.btn_web.model.Term;
import com.dzungyb.btn_web.repository.TermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TermService {

    @Autowired
    TermRepository termRepository;

    public void saveTerm(Term term){
        termRepository.save(term);
    }

    public List<Term> getListTerm(){
        return (List<Term>) termRepository.findAll();
    }
}
