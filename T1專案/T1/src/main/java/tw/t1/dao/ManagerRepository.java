package tw.t1.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.t1.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {

}
