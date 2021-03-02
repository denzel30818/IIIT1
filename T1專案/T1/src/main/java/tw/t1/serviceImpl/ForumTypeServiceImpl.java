package tw.t1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.t1.dao.ForumTypeRepository;
import tw.t1.entity.ForumType;
import tw.t1.service.ForumTypeService;

@Service
public class ForumTypeServiceImpl implements ForumTypeService {

	@Autowired
	private ForumTypeRepository forumTypeRepository;

	@Override
	public List<ForumType> findAll() {

		return forumTypeRepository.findAll();
	}

	@Override
	public ForumType findById(int Id) {
		Optional<ForumType> result = forumTypeRepository.findById(Id);
		ForumType forumType = null;
		if (result.isPresent()) {
			forumType = result.get();
		} else {
			throw new RuntimeException("找不到此ID" + Id);
		}
		return forumType;
	}

	@Override
	public void save(ForumType forumType) {
		forumTypeRepository.save(forumType);

	}

	@Override
	public void deleteById(int Id) {
		forumTypeRepository.deleteById(Id);

	}

	@Override
	public List<ForumType> findByCategory(String category) {
		return forumTypeRepository.findByCategory(category);
	}

	@Override
	public long countByForumName(String forumName) {

		return forumTypeRepository.countByForumName(forumName);
	}

	// 搜尋看板
	@Override
	public List<ForumType> searchForumByForumName(String forumName) {
		return forumTypeRepository.searchForumByForumName(forumName);
	}

}
