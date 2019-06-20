package at.fh.swenga.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.jpa.model.SongModel;

@Repository
@Transactional
public interface SongRepository extends JpaRepository<SongModel, Integer>{
	
	SongModel findTopById(int id);

	int countByQuizzesId(int id);
	
	List<SongModel> findByQuizzesId(int quizId);
}
