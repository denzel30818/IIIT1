package tw.t1.service;

import java.util.List;

import tw.t1.entity.Announcement;

public interface AnnouncementService {

	public List<Announcement> findAll();

	public Announcement findById(int Id);

	public void save(Announcement Announcement);

	public void deleteById(int Id);

	public List<Announcement> findAnnouncementByForumIdByDesc(int forumID);

	public List<Announcement> findAllByDesc();

	public List<Announcement> findWithLikeDesc(String forumName);
}
