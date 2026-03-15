package com.abacus.franchise.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abacus.franchise.dto.BasicUserDetail;
import com.abacus.franchise.model.AssignExam;

import jakarta.transaction.Transactional;

@Repository
public interface AssignExamRepository extends JpaRepository<AssignExam, UUID> {

	@Query(value = """
			SELECT DISTINCT 
			   u.user_id,
			   u.first_name,
			   u.middle_name,
			   u.last_name,
			   ae.exam_marks
			FROM assign_exam ae 
			JOIN assign_exam_student aes ON aes.assign_exam_id = ae.assign_exam_id
			JOIN exam e ON ae.exam_id = e.exam_id
			JOIN users u ON u.user_id = aes.student_id
			WHERE ae.franchise_id = :franchiseId AND e.course_id = :courseId AND ae.is_active = true AND exam_status = :examStatus AND ae.exam_mode = :examMode
	""",nativeQuery = true)
	List<BasicUserDetail> getAllExamStudentsByCourseId(String franchiseId, String courseId,String examStatus,String examMode);

	@Query(value="""
			SELECT ae.assign_exam_id 
			FROM assign_exam ae 
			JOIN assign_exam_student aes ON aes.assign_exam_id = ae.assign_exam_id
			WHERE 
				ae.franchise_id = :franchiseId 
				AND ae.is_active = true 
				AND aes.student_id = :studentId
				AND ae.exam_status = 'COMPLETED'
			ORDER BY ae.created_at DESC LIMIT 1
	""",nativeQuery = true)
	UUID checkExamCompltedOrNot(String franchiseId,String studentId);
	
	@Modifying
	@Transactional
	@Query(value="""
	  UPDATE assign_exam SET exam_status = 'RE_ASSIGNED' WHERE assign_exam_id = :examId		
	""",nativeQuery = true)
	int changeExamStatusByExamId(@Param("examId") String examId);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE assign_exam SET exam_status = 'COMPLETED' ,correct_que = :countMarks, exam_marks = :countMarks ,start_date = :startTime , submit_date = :endTime WHERE assign_exam_id = :assignExamId",nativeQuery = true)
	int updateMarksByExamId(@Param("countMarks") int countMarks,@Param("assignExamId") String assignExamId,@Param("startTime") String startTime,@Param("endTime") String endTime);

	
	
	
}
