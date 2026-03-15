package com.abacus.franchise.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.abacus.franchise.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question,UUID>{

	@Query(value = "SELECT correct_answer FROM question WHERE question_id = :questionId",nativeQuery = true)
	String getCorrectAnswerByQuestionId(String questionId);
}
