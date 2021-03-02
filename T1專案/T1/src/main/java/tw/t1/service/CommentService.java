package tw.t1.service;

import java.util.List;

import tw.t1.entity.Comment;

public interface CommentService {

	public Comment findById(int id);

	public void deleteById(int id);

	public List<Comment> findAll();

	public Comment save(Comment comment);
	
	public List<Comment> findCommentByArticleId(int id);
	
	public void deleteCommentByArticleId(int id);
}
