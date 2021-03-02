package tw.t1.service;

import java.util.List;

import tw.t1.entity.ForumType;

public interface ForumTypeService {

	public List<ForumType> findAll();

	public ForumType findById(int Id);

	public void save(ForumType forumType);

	public void deleteById(int Id);

	public List<ForumType> findByCategory(String category);

	public long countByForumName(String forumName);

	public List<ForumType> searchForumByForumName(String forumName);

}
