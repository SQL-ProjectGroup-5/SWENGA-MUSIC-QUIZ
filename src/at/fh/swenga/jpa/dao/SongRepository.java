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
	
	// fetches songs with results to mitigate lazy initialization
	@Query("SELECT DISTINCT s FROM SongModel s LEFT JOIN FETCH s.results sr")
	List<SongModel> findAllWithResults();
	
	// fetches songs with results to mitigate lazy initialization
	@Query("SELECT DISTINCT s FROM SongModel s LEFT JOIN FETCH s.results WHERE s.user.id =:id")
	List<SongModel> findByUserIdWithResults(@Param("id") int userId);

	//for Admin, list all documents with uploaded song
	@Query("SELECT s FROM SongModel s WHERE s.document.id IS NOT NULL")
	List<SongModel> findWhereDocIdNotNull();

	//for User, list all documents with uploaded song
	@Query("SELECT s FROM SongModel s WHERE s.document.id IS NOT NULL AND s.user.id=:id")
	List<SongModel> findWhereDocIdNotNullAndUserRole(@Param("id") int userId);
}
