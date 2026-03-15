package com.abacus.franchise.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abacus.franchise.model.AssignExamStudent;

@Repository
public interface AssignExamStudentRepository extends JpaRepository<AssignExamStudent, UUID> {

}
