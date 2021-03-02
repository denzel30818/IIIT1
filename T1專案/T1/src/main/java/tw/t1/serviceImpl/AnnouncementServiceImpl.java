package tw.t1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.t1.dao.AnnouncementRepository;
import tw.t1.entity.Announcement;
import tw.t1.service.AnnouncementService;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

	@Autowired
	private AnnouncementRepository announcementRepository;

	@Override
	public List<Announcement> findAll() {
		return announcementRepository.findAll();
	}

	@Override
	public Announcement findById(int id) {

		Optional<Announcement> result = announcementRepository.findById(id);

		Announcement announcement = null;

		if (result.isPresent()) {
			announcement = result.get();
		} else {
			throw new RuntimeException("找不到id為" + id + "的公告");
		}
		return announcement;
	}

	@Override
	public void save(Announcement announcement) {
		announcementRepository.save(announcement);
	}

	@Override
	public void deleteById(int id) {
		announcementRepository.deleteById(id);
	}

	@Override
	public List<Announcement> findAnnouncementByForumIdByDesc(int forumID) {
		return announcementRepository.findAnnouncementByForumIdByDesc(forumID);
	}

	@Override
	public List<Announcement> findAllByDesc() {
		return announcementRepository.findAllByDesc();
	}

	@Override
	public List<Announcement> findWithLikeDesc(String forumName) {
		return announcementRepository.findWithLikeDesc(forumName);
	}

}
