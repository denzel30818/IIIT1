package tw.t1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.t1.dao.CommentRepository;
import tw.t1.entity.Comment;
import tw.t1.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public Comment findById(int id) {

		Optional<Comment> result = commentRepository.findById(id);

		Comment comment = null;

		if (result.isPresent()) {
			comment = result.get();
		} else {
			throw new RuntimeException("找不到id為" + id + "的文章");
		}
		return comment;

	}

	@Override
	public void deleteById(int id) {
		commentRepository.deleteById(id);
	}

	@Override
	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	@Override
	public Comment save(Comment comment) {
		return commentRepository.save(comment);
	}

	@Override
	public List<Comment> findCommentByArticleId(int id) {
		return commentRepository.findCommentByArticleId(id);
	}

	@Override
	public void deleteCommentByArticleId(int id) {
		commentRepository.deleteCommentByArticleId(id);
	}

}
