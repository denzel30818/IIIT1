package tw.t1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tw.t1.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	// 依信箱查找
	@Query(value = "SELECT * FROM users where email = ?1", nativeQuery = true)
	public User findUserByEmail(String email);

	// 依UUID查找
	@Query(value = "SELECT * FROM users where code = ?1", nativeQuery = true)
	public User findUserByCode(String code);

	// 依帳號查找
	@Query(value = "SELECT * FROM users where account = ?1", nativeQuery = true)
	public User findUserByAccount(String account);

	// 依限定範圍查找
	@Query(value = "SELECT * FROM users LIMIT ?1,?2", nativeQuery = true)
	public List<User> findUserByLimit(int start, int end);

	// 查詢黑名單 ( 依ID排序 )
	@Query(value = "SELECT * FROM users WHERE banned = 'y' ORDER BY userID", nativeQuery = true)
	public List<User> findUserByBanned();

	// 查詢黑名單 ( 依ID排序，指定查詢範圍)
	@Query(value = "SELECT * FROM users WHERE banned = 'y' ORDER BY userID LIMIT ?1,?2", nativeQuery = true)
	public List<User> findUserByBannedLimit(int start, int end);

	// 查詢當月壽星 ( 不含黑名單 )
	@Query(value = "SELECT * FROM users WHERE banned = 'n' and DATE_FORMAT(birthDate,'%m')=DATE_FORMAT(CURDATE(),'%m') ORDER BY userID", nativeQuery = true)
	public List<User> findUserByBirthDateWithUnbannedThisMonth();

	// 查詢當月壽星 ( 不含黑名單，指定查詢範圍 )
	@Query(value = "SELECT * FROM users WHERE banned = 'n' and DATE_FORMAT(birthDate,'%m')=DATE_FORMAT(CURDATE(),'%m') ORDER BY userID LIMIT ?1,?2", nativeQuery = true)
	public List<User> findUserByBirthDateWithUnbannedThisMonthLimit(int start, int end);

	// 依名稱篩選
	@Query(value = "SELECT * FROM users WHERE UPPER(nickName) LIKE UPPER(CONCAT('%',?1,'%'))", nativeQuery = true)
	public List<User> findUserWithLike(String key);

	// 查詢篩選過的當月壽星 ( 不含黑名單 )
	@Query(value = "SELECT * FROM users WHERE banned = 'n' and DATE_FORMAT(birthDate,'%m')=DATE_FORMAT(CURDATE(),'%m') and UPPER(nickName) LIKE UPPER(CONCAT('%',?1,'%')) ORDER BY userID", nativeQuery = true)
	public List<User> findUserByBirthDateWithUnbannedThisMonthByKey(String key);

	// 查詢篩選過的黑名單 ( 依ID排序 )
	@Query(value = "SELECT * FROM users WHERE banned = 'y' and UPPER(nickName) LIKE UPPER(CONCAT('%',?1,'%')) ORDER BY userID", nativeQuery = true)
	public List<User> findUserByBannedByKey(String key);
}
