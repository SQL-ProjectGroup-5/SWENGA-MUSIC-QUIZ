package at.fh.swenga.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.jpa.model.Comment;
import at.fh.swenga.jpa.model.QuizModel;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Integer> 
{

}
