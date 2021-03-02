package tw.t1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.t1.dao.GameTypeRepository;
import tw.t1.entity.GameType;
import tw.t1.service.GameTypeService;

@Service
public class GameTypeServiceImpl implements GameTypeService {
	
	@Autowired
	private GameTypeRepository gRep;
	
	public GameTypeServiceImpl(GameTypeRepository gRep) {
		this.gRep=gRep;
	}
	
	@Override
	public List<GameType> findAll() {
		return gRep.findAll();
	}

	@Override
	public GameType findById(int Id) {
		Optional<GameType> result = gRep.findById(Id);
		GameType gameType = null;
		if(result.isPresent()) {
			gameType = result.get();
		}
		else {
			throw new RuntimeException("找不到此ID" + Id);
		}
		
		return gameType;
	}

	@Override
	public void save(GameType gameType) {
		gRep.save(gameType);
	}

	@Override
	public void deleteById(int Id) {
		gRep.deleteById(Id);
	}

	@Override
	public GameType findByGametype(String gameType) {
		return gRep.findByGametype(gameType);
	}

}
