package com.abacus.franchise.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abacus.franchise.dto.StudentCourseExamProjection;
import com.abacus.franchise.model.StudentCourse;

import jakarta.transaction.Transactional;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, UUID>{

	@Query(value = "SELECT student_course_id FROM student_course WHERE course_id = :courseId AND franchise_id = :franchiseId AND student_id = :studentId LIMIT 1",nativeQuery = true)
	UUID checkCourseAlreadyExistOrNot(String courseId,String franchiseId,String studentId);
	
	@Modifying
	@Transactional
	@Query(value = """
		UPDATE student_course sc
			JOIN (
			    SELECT student_course_id
			    FROM student_course
			    WHERE student_id = :studentId
			      AND franchise_id = :franchiseId
			    ORDER BY created_at DESC
			    LIMIT 1
			) AS latest
			ON sc.student_course_id = latest.student_course_id
			SET sc.course_active = FALSE;
	""",nativeQuery = true)
	void courseDisActiveByFranchiseAndStudentId(String franchiseId,String studentId);
	
	 @Query(value = """
		SELECT 
			sc.course_active,
			e.exam_name,
			e.question_count,
			e.image_que_percentage,
			c.course_name,
			c.duration_days,
			c.course_type,
			ae.assign_date,
			ae.exam_mode,
			ae.exam_time,
			ae.exam_marks,
			ae.exam_status,
			ae.submit_date,
			ae.start_date,
			ae.exam_attempt     
		FROM student_course sc
			JOIN course c ON c.course_id = sc.course_id AND c.is_active = 1
			JOIN exam e ON e.course_id = sc.course_id AND e.is_active = 1
			LEFT JOIN assign_exam_student aes ON aes.student_id = sc.student_id 
			LEFT JOIN assign_exam ae ON ae.assign_exam_id = aes.assign_exam_id AND ae.is_active = 1
		WHERE sc.student_id = :studentId
	            """, nativeQuery = true)

	    List<StudentCourseExamProjection> getStudentCourseExamDetails(@Param("studentId") String studentId);
	
	
	
}
