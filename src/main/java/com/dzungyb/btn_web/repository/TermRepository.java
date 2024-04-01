package com.dzungyb.btn_web.repository;

import com.dzungyb.btn_web.model.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermRepository extends JpaRepository<Term, Integer> {

}
