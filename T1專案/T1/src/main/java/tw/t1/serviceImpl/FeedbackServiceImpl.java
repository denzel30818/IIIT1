package tw.t1.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.t1.dao.FeedbackRepository;
import tw.t1.entity.Feedback;
import tw.t1.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {
	
	@Autowired
	private FeedbackRepository fRep;
	
	@Override
	public List<Feedback> findAll() {
		return fRep.findAll();
	}

	@Override
	public Feedback findById(int Id) {
		return fRep.findFeedbackById(Id);
	}

	@Override
	public void save(Feedback feedback) {
		fRep.save(feedback);
	}

	@Override
	public void deleteById(int Id) {
		fRep.deleteById(Id);
	}

	@Override
	public List<Feedback> findFeedbackByState(String state) {
		return fRep.findFeedbackByState(state);
	}

}
