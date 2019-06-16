package at.fh.swenga.jpa.dao;
 
import java.util.List;
 
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.jpa.model.User;
 
@Repository
@Transactional
public class UserDao {
 
	@PersistenceContext
	protected EntityManager entityManager;
 
	public List<User> findByUsername(String userName) {
		TypedQuery<User> typedQuery = entityManager.createQuery("select u from User u where u.userName = :name",
				User.class);
		typedQuery.setParameter("name", userName);
		List<User> typedResultList = typedQuery.getResultList();
		return typedResultList;
	}
	public List<User> findAll() {
		TypedQuery<User> typedQuery = entityManager.createQuery("select u from User u",
				User.class);
		List<User> typedResultList = typedQuery.getResultList();
		return typedResultList;
	}
	
	
	public User getUser(int i) throws DataAccessException {
		return entityManager.find(User.class, i);
	}
	
	public void delete(int id) {
		Query query = entityManager.createQuery("DELETE FROM User u WHERE u.id = :i");
		int deleteCount = query.setParameter("i", id).executeUpdate();
		return;
	}
 
	public void persist(User user) {
		entityManager.persist(user);
	}
}