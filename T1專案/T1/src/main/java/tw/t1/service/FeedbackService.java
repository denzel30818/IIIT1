package tw.t1.service;

import java.util.List;

import tw.t1.entity.Feedback;

public interface FeedbackService {
	

	public List<Feedback> findAll();

	public Feedback findById(int Id);

	public void save(Feedback feedback);

	public void deleteById(int Id);
	
	public List<Feedback> findFeedbackByState(String state);

}
