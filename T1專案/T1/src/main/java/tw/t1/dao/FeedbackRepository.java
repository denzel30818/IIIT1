package tw.t1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tw.t1.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
	
	
	@Query(value = "SELECT * FROM feedback where id = ?1", nativeQuery = true)
	public Feedback findFeedbackById(int Id);
	
	@Query(value = "SELECT * FROM feedback where state = ?1 order by date desc", nativeQuery = true)
	public List<Feedback> findFeedbackByState(String state);
	
}
