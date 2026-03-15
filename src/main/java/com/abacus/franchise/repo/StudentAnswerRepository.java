package com.abacus.franchise.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.abacus.franchise.dto.ExamAttemptSummaryDTO;
import com.abacus.franchise.enums.ExamType;
import com.abacus.franchise.model.StudentAnswer;

@Repository
public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, UUID> {

	@Query(value = "SELECT exam_attempt FROM student_answer WHERE assign_exam_id = :assignExamId AND student_id = :studentId", nativeQuery = true)
	Integer getExamAttemptByAssignExamIdAndStudentId(String studentId, String assignExamId);

	@Query("""
								SELECT new com.abacus.franchise.dto.ExamAttemptSummaryDTO(
								    sa.examAttempt ,
								    COUNT(sa.questionId),

						SUM(CASE WHEN sa.isCorrect = true THEN 1 ELSE 0 END),
								    SUM(CASE WHEN sa.isCorrect = true THEN 1 ELSE 0 END)
								)
								FROM StudentAnswer sa
								WHERE sa.studentId = :studentId
								AND sa.assignExamId = :assignExamId
								AND sa.examType = :examType
								GROUP BY sa.examAttempt
								ORDER BY sa.examAttempt
			""")
	List<ExamAttemptSummaryDTO> getExamAttemptSummary(
			UUID studentId,
			UUID assignExamId,
			ExamType examType);
}
