package tw.t1.service;

import java.util.List;

import tw.t1.entity.User;

public interface UserService {

	public List<User> findAll();

	public User findById(int id);

	public User save(User user);

	public void deleteById(int id);

	public User findUserByEmail(String email);

	public User findUserByCode(String code);

	public User findUserByAccount(String account);

	public List<User> findUserByLimit(int start, int end);

	public List<User> findUserByBanned();

	public List<User> findUserByBannedLimit(int start, int end);

	public List<User> findUserByBirthDateWithUnbannedThisMonth();

	public List<User> findUserByBirthDateWithUnbannedThisMonthLimit(int start, int end);

	public List<User> findUserWithLike(String key);

	public List<User> findUserByBirthDateWithUnbannedThisMonthByKey(String key);

	public List<User> findUserByBannedByKey(String key);
}
