package com.abacus.franchise.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abacus.franchise.model.FranchiseCourse;

@Repository
public interface FranchiseCourseRepository extends JpaRepository<FranchiseCourse, UUID> {

	@Query(value = "SELECT franchise_course_id FROM franchise_course WHERE course_id = :courseId  AND franchise_id = :franchiseId AND courses_status = 1",nativeQuery = true)
	UUID checkCourseExistOrNotInFranchise(@Param("courseId") String courseId,@Param("franchiseId") String franchiseId);
}
