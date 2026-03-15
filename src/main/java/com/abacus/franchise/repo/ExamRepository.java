package com.abacus.franchise.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abacus.franchise.dto.ExamDetail;
import com.abacus.franchise.dto.ExamDetailProjection;
import com.abacus.franchise.dto.QuestionProjection;
import com.abacus.franchise.model.Exam;

import jakarta.transaction.Transactional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, UUID> {
	
	@Query(value = """
		SELECT 
		   exam_id AS examId,exam_name AS examName,exam_time AS examTime
		FROM exam 
		WHERE course_id = :courseId AND is_active = true;
	""",nativeQuery = true)
	List<ExamDetail> getAllExamDetailByCourseId(String courseId);

	
	@Query(value = """
			SELECT 
			   exam_id
			FROM exam 
			WHERE exam_id = :examId AND is_active = true;
		""",nativeQuery = true)
	UUID checkExamIdIsPresentOrNot(String examId);
	
	@Query(value = """
			SELECT 
			   final_paper_link
			FROM exam 
			WHERE course_id = :courseId AND exam_mode = 'OFFLINE' AND is_active = true;
		""",nativeQuery = true)
	List<String> getFinalPaperByCourseId(String courseId);
	
	@Query(value = """
			SELECT 
			   practice_paper_link
			FROM exam 
			WHERE course_id = :courseId AND exam_mode = 'OFFLINE' AND is_active = true;
		""",nativeQuery = true)
	List<String> getPracticePaperByCourseId(String courseId);
	
	@Query(value = """
		SELECT 
		    DISTINCT ae.assign_exam_id 
		FROM assign_exam ae
			JOIN assign_exam_student aes ON aes.assign_exam_id = ae.assign_exam_id
		WHERE ae.exam_id = :examId AND aes.student_id = :studentId LIMIT 1;
    """,nativeQuery = true)
	UUID getAssignExamIdByExamAndStudentId(@Param("examId") String examId,@Param("studentId") String studentId);
	
	  @Query(value = """
		SELECT DISTINCT
		   ae.assign_exam_id AS assignExamId,
		   ae.exam_time AS examTime,
		   e.question_count AS questionCount,
		   e.exam_name AS examName
		FROM assign_exam_student aes 
		JOIN assign_exam ae 
		     ON ae.assign_exam_id = aes.assign_exam_id 
		     AND ae.is_active = 1
		JOIN exam e 
		     ON e.exam_id = ae.exam_id 
		     AND e.is_active = 1
		WHERE aes.student_id = :studentId
		AND ae.exam_status != 'COMPLETED'
		AND ae.exam_mode = 'ONLINE'
		""", nativeQuery = true)
	ExamDetailProjection getExamDetails(String studentId);
	
	
	@Query(value = """
		SELECT DISTINCT
		   q.question_id AS questionId,
		   q.question_type AS questionType,
		   q.question_text AS questionText,
		   q.question_link AS questionLink,
		   q.option_a AS optionA,
		   q.option_b AS optionB,
		   q.option_c AS optionC,
		   q.option_d AS optionD
		FROM assign_exam_student aes 
		JOIN assign_exam ae 
		     ON ae.assign_exam_id = aes.assign_exam_id 
		     AND ae.is_active = 1
		JOIN question q 
		     ON q.exam_id = ae.exam_id 
		     AND q.is_active = 1
		WHERE aes.student_id = :studentId
		AND ae.exam_status != 'COMPLETED'
		AND ae.exam_mode = 'ONLINE'
		""", nativeQuery = true)
	List<QuestionProjection> getExamQuestions(String studentId);


}
