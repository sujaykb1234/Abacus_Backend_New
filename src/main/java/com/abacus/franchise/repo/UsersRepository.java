package com.abacus.franchise.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abacus.franchise.dto.CredentialDetail;
import com.abacus.franchise.dto.UserAddressDetail;
import com.abacus.franchise.dto.UserDetail;
import com.abacus.franchise.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID>{

	@Query(value="""
		SELECT u.user_id 
		FROM users u 
		JOIN roles r ON r.role_id = u.role_id
		WHERE u.user_id = :userId AND r.role_name IN :roles AND u.is_active = true
					
	""",nativeQuery = true)
	UUID checkUserIsExistOrNotByIdOrStatus(String userId,List<String> roles);
	
	
	@Query(value="""
		SELECT 
		  u.password_hash,u.is_active
		FROM users u 
		 JOIN roles r ON r.role_id = u.role_id
		 WHERE u.mobile = :username AND r.role_name = :rolename	AND u.user_id = :userId			
	""",nativeQuery = true)
	CredentialDetail checkMobileNoIsPresentOrNot(String username,String rolename,String userId);

	@Query(value="""
	SELECT u.user_id,
		       u.date_of_birth,
			   u.email,
			   u.first_name,
		       u.franchise_name,
		       u.last_name,
		       u.middle_name,
		       u.mobile,
		       u.profile_link,
		       u.document_link,
		       a.line1,
		       a.city,
		       a.landmark,
		       a.country_name,
		       a.pincode,
		       s.state_name,
		       d.district_name,
		       u.franchise_id
		FROM users u 
		JOIN address a on a.user_id = u.user_id AND a.is_active = true
		JOIN state s on s.state_id = a.state_id AND s.is_active = true
		JOIN district d on d.district_id = a.district_id AND d.is_active = true
		JOIN roles r on r.role_id = u.role_id AND r.is_active = true
		WHERE u.user_id = :userId  AND r.role_name IN :roleName  AND u.is_active = TRUE ;			
	""",nativeQuery = true)
	UserAddressDetail getUserAddressDetailByUserId(@Param("userId") String userId,@Param("roleName") List<String> roleName);
	
	@Query(value="""
	SELECT u.user_id,
		       u.date_of_birth,
			   u.email,
			   u.first_name,
		       u.franchise_name,
		       u.last_name,
		       u.middle_name,
		       u.mobile,
		       u.profile_link,
		       u.document_link,
		       a.line1,
		       a.city,
		       a.landmark,
		       a.country_name,
		       a.pincode,
		       s.state_name,
		       d.district_name
		FROM users u 
		JOIN address a on a.user_id = u.user_id AND a.is_active = true
		JOIN state s on s.state_id = a.state_id AND s.is_active = true
		JOIN district d on d.district_id = a.district_id AND d.is_active = true
		JOIN roles r on r.role_id = u.role_id AND r.is_active = true
		WHERE u.franchise_id = :franchiseId AND u.is_active = TRUE ;			
	""",nativeQuery = true)
	List<UserAddressDetail> getStudentDetailByFranchiseId(@Param("franchiseId") String franchiseId);

	
	@Query(value="SELECT user_id FROM users where mobile = :mobile and is_active = true ",nativeQuery = true)
	UUID checkMobileNoIsExistOrNot(@Param("mobile") String mobile);
	
	@Query(value="SELECT user_id FROM users where email = :email and is_active = true",nativeQuery = true)
	UUID checkEmailIsExistOrNot(@Param("email") String email);
	
	@Query(value = """
		SELECT DISTINCT u.user_id,
		               u.first_name,
		               u.last_name,
		               u.middle_name,
		               u.profile_link
		FROM users u 
			JOIN roles r ON r.role_id = u.role_id
			JOIN assign_exam_student aes ON aes.student_id != u.user_id
			JOIN assign_exam ae ON ae.assign_exam_id = aes.assign_exam_id
		WHERE r.role_name = :role  AND ae.exam_id = :examId;
	""",nativeQuery = true)
	List<UserDetail> getAllUnAssignStudentByExamId(@Param("examId")String examId,@Param("role")String role );
	
	
}
