package com.abacus.franchise.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.abacus.franchise.model.StudentAnswer;

@Repository
public interface StudentAnswerRepository extends JpaRepository<StudentAnswer,UUID> {

	@Query(value = "SELECT exam_attempt FROM student_answer WHERE question_id = :questionId AND student_id = :studentId",nativeQuery = true)
	Integer getExamAttemptByQuestionIdAndStudentId(String studentId,String questionId);
	
}
