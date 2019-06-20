package at.fh.swenga.jpa.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.jpa.model.QuizModel;

@Repository
@Transactional
public interface QuizRepository extends JpaRepository<QuizModel, Integer> {
	QuizModel findTopById(int id);
	
}
