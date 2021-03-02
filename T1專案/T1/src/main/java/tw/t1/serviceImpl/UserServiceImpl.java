package tw.t1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.t1.dao.UserRepository;
import tw.t1.entity.User;
import tw.t1.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findById(int id) {

		Optional<User> result = userRepository.findById(id);

		User user = null;

		if (result.isPresent()) {
			user = result.get();
		} else {
			throw new RuntimeException("找不到id為" + id + "的使用者");
		}
		return user;
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteById(int id) {
		userRepository.deleteById(id);
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}

	@Override
	public User findUserByCode(String code) {
		return userRepository.findUserByCode(code);
	}

	@Override
	public User findUserByAccount(String account) {
		return userRepository.findUserByAccount(account);
	}

	@Override
	public List<User> findUserByLimit(int start, int end) {
		return userRepository.findUserByLimit(start, end);
	}

	@Override
	public List<User> findUserByBanned() {
		return userRepository.findUserByBanned();
	}

	@Override
	public List<User> findUserByBannedLimit(int start, int end) {
		return userRepository.findUserByBannedLimit(start, end);
	}

	@Override
	public List<User> findUserByBirthDateWithUnbannedThisMonth() {
		return userRepository.findUserByBirthDateWithUnbannedThisMonth();
	}

	@Override
	public List<User> findUserByBirthDateWithUnbannedThisMonthLimit(int start, int end) {
		return userRepository.findUserByBirthDateWithUnbannedThisMonthLimit(start, end);
	}

	@Override
	public List<User> findUserWithLike(String key) {
		return userRepository.findUserWithLike(key);
	}

	@Override
	public List<User> findUserByBirthDateWithUnbannedThisMonthByKey(String key) {
		return userRepository.findUserByBirthDateWithUnbannedThisMonthByKey(key);
	}

	@Override
	public List<User> findUserByBannedByKey(String key) {
		return userRepository.findUserByBannedByKey(key);
	}

}
