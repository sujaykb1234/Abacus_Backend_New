package com.abacus.franchise.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abacus.franchise.dto.QuestionProjection;
import com.abacus.franchise.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {

	@Query(value = "SELECT correct_answer FROM question WHERE question_id = :questionId", nativeQuery = true)
	String getCorrectAnswerByQuestionId(String questionId);

	@Query(value = """
			SELECT
			    question_id AS questionId,
			    question_type AS questionType,
			    question_text AS questionText,
			    question_link AS questionLink,
			    option_a AS optionA,
			    option_b AS optionB,
			    option_c AS optionC,
			    option_d AS optionD
			FROM (
			    SELECT
			        q.question_id,
			        q.question_type,
			        q.question_text,
			        q.question_link,
			        q.option_a,
			        q.option_b,
			        q.option_c,
			        q.option_d,
			        ROW_NUMBER() OVER (PARTITION BY q.question_type ORDER BY RAND()) AS type_rank,
			        ROW_NUMBER() OVER (ORDER BY RAND()) AS overall_rank
			    FROM question q
			    JOIN exam e ON q.exam_id = e.exam_id
			    WHERE e.is_active = 1
			    AND e.course_id = :courseId
			) t
			WHERE type_rank = 1 OR overall_rank <= :limit
			ORDER BY RAND()
			LIMIT :limit
			""", nativeQuery = true)
	List<QuestionProjection> getRandomQuestions(
			@Param("courseId") String courseId,
			@Param("limit") int limit);

}
