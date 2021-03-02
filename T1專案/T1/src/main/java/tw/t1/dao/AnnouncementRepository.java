package tw.t1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tw.t1.entity.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {

	// 找指定論壇小類的公告 ( 倒序 )
	@Query(value = "SELECT * FROM announcement WHERE forum_id = ?1 ORDER BY id DESC", nativeQuery = true)
	public List<Announcement> findAnnouncementByForumIdByDesc(int forumID);

	// 找全部公告 ( 倒序 )
	@Query(value = "SELECT * FROM announcement ORDER BY id DESC", nativeQuery = true)
	public List<Announcement> findAllByDesc();

	// 找篩選過的公告( 倒序 )
	@Query(value = "SELECT * FROM announcement WHERE UPPER(forum_Name) LIKE UPPER(CONCAT('%',?1,'%')) ORDER BY id DESC", nativeQuery = true)
	public List<Announcement> findWithLikeDesc(String forumName);
}
