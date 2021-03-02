package tw.t1.service;

import java.util.List;

import tw.t1.entity.Manager;

public interface ManagerService {

	public List<Manager> findAll();

	public Manager findById(int managerID);

	public Manager save(Manager manager);

	public void deleteById(int managerID);
}
