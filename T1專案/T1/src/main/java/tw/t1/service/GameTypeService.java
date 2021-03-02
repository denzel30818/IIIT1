package tw.t1.service;

import java.util.List;

import tw.t1.entity.GameType;

public interface GameTypeService {

	public List<GameType> findAll();

	public GameType findById(int Id);

	public void save(GameType gameType);

	public void deleteById(int Id);

	public GameType findByGametype(String gameType);
}
