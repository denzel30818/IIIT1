package tw.t1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.t1.dao.ManagerRepository;
import tw.t1.entity.Manager;
import tw.t1.service.ManagerService;

@Service
public class ManagerServiceImpl implements ManagerService {

	@Autowired
	private ManagerRepository mr;

	@Override
	public List<Manager> findAll() {
		return mr.findAll();
	}

	@Override
	public Manager findById(int managerID) {

		Optional<Manager> result = mr.findById(managerID);

		Manager manager = null;

		if (result.isPresent()) {
			manager = result.get();
		} else {
			throw new RuntimeException("找不到id為" + managerID + "的使用者");
		}
		return manager;
	}

	@Override
	public Manager save(Manager manager) {
		return mr.save(manager);
	}

	@Override
	public void deleteById(int managerID) {
		mr.deleteById(managerID);
	}

}
