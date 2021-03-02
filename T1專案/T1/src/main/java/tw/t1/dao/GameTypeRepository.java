package tw.t1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tw.t1.entity.GameType;

public interface GameTypeRepository extends JpaRepository<GameType, Integer> {
	

	@Query(value = "SELECT * FROM gametype WHERE gametype = ?1", nativeQuery = true) 
	public GameType findByGametype(String gameType);
	
}
