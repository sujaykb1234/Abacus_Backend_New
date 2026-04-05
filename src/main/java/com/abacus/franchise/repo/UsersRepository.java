package com.abacus.franchise.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abacus.franchise.dto.CredentialDetail;
import com.abacus.franchise.dto.FranchiseProjection;
import com.abacus.franchise.dto.UserAddressDetail;
import com.abacus.franchise.dto.UserDetail;
import com.abacus.franchise.dto.UserRoleProjection;
import com.abacus.franchise.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {

	@Query(value = """
				SELECT u.user_id
				FROM users u
				JOIN roles r ON r.role_id = u.role_id
				WHERE u.user_id = :userId AND r.role_name IN :roles AND u.is_active = true

			""", nativeQuery = true)
	UUID checkUserIsExistOrNotByIdOrStatus(String userId, List<String> roles);

	@Query(value = """
				SELECT
				  u.password_hash,u.is_active
				FROM users u
				 JOIN roles r ON r.role_id = u.role_id
				 WHERE u.mobile = :username
			""", nativeQuery = true)
	CredentialDetail checkMobileNoIsPresentOrNot(String username);

	@Query(value = """
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
			""", nativeQuery = true)
	UserAddressDetail getUserAddressDetailByUserId(@Param("userId") String userId,
			@Param("roleName") List<String> roleName);

	@Query(value = """
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
				WHERE u.franchise_id = :franchiseId AND  r.role_name = "STUDENT" AND u.is_active = TRUE ;
			""", nativeQuery = true)
	List<UserAddressDetail> getStudentDetailByFranchiseId(@Param("franchiseId") String franchiseId);

	@Query(value = "SELECT user_id FROM users where mobile = :mobile and is_active = true ", nativeQuery = true)
	UUID checkMobileNoIsExistOrNot(@Param("mobile") String mobile);

	@Query(value = "SELECT user_id FROM users where email = :email and is_active = true", nativeQuery = true)
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
			""", nativeQuery = true)
	List<UserDetail> getAllUnAssignStudentByExamId(@Param("examId") String examId, @Param("role") String role);

	@Query(value = """
			SELECT
			u.user_id,
			u.first_name,
			u.franchise_name,
			u.last_name,
			u.middle_name,
			u.mobile,
			r.role_name,
			f.franchise_name AS parentFranchiseName

			FROM users u
			JOIN roles r ON r.role_id = u.role_id AND r.is_active = true
			LEFT JOIN users f ON f.user_id = u.franchise_id AND f.is_active = true

			WHERE r.role_name IN ('FRANCHISE','MASTER_FRANCHISE')
			AND u.is_active = true
			AND (
			      (:franchiseId IS NOT NULL AND u.franchise_id = :franchiseId)
			   OR (:franchiseId IS NULL AND u.franchise_id IS NULL)
			)
			AND (
			      (:mobile IS NOT NULL AND u.mobile LIKE :mobile)
			   OR (:mobile IS NULL)
			)
			AND (
			      (:fromDate IS NOT NULL AND :toDate IS NOT NULL AND u.created_at BETWEEN :fromDate AND :toDate)
			   OR (:fromDate IS NULL AND :toDate IS NULL)
			)
			""", nativeQuery = true)
	Page<FranchiseProjection> getAllFranchises(
			@Param("franchiseId") String franchiseId,
			@Param("mobile") String mobile,
			@Param("fromDate") String fromDate,
			@Param("toDate") String toDate,
			Pageable pageable);

	@Query(value = """
			SELECT u.user_id AS userId,
			       r.role_name AS roleName
			FROM users u
			JOIN roles r ON r.role_id = u.role_id AND r.is_active = true
			WHERE u.mobile = :mobile AND u.is_active = true
			""", nativeQuery = true)
	Optional<UserRoleProjection> findUserRoleByMobile(@Param("mobile") String mobile);

}
