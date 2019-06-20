package at.fh.swenga.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.jpa.model.SongModel;

@Repository
@Transactional
public interface SongRepository extends JpaRepository<SongModel, Integer> {

	SongModel findTopById(int id);

	int countByQuizzesId(int id);

	List<SongModel> findByQuizzesId(int quizId);

	List<SongModel> findByUserId(int userId);

	@Query("SELECT s FROM SongModel s LEFT JOIN FETCH s.results sr")
	List<SongModel> findAllWithResults();
	
	@Query("SELECT s FROM SongModel s LEFT JOIN FETCH s.results WHERE s.user.id =:id")
	List<SongModel> findByUserIdWithResults(@Param("id") int userId);

}
