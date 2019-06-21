package at.fh.swenga.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.jpa.model.ResultModel;
@Repository 
@Transactional
public interface ResultRepository extends JpaRepository<ResultModel, Integer> {

	
	public List<ResultModel> findBySessionIDAndQuizId(String sessionID, int quizID);
	
	//@Query("SELECT COUNT(s) FROM ResultModel s WHERE s.correct IS TRUE");
	//int
	int countByCorrect(boolean ok);
	
}
