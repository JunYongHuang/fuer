package com.cf.fuer.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.fuer.dao.IGameServerDao;
import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.manager.IGameServerManager;
import com.cf.fuer.model.GameServer;
import com.cf.fuer.validator.GameServerValidator;
import com.cf.fuer.webapp.bean.SearchBean;

@Service
public class GameServerManagerImpl implements IGameServerManager {

	@Autowired
	private IGameServerDao gameServerDao;

	@Autowired
	private GameServerValidator gameServerValidator;

	@Override
	public Long create(GameServer gameServer) throws ValidationException {
		gameServerValidator.validateCreate(gameServer);
		Long id = gameServerDao.createGameServer(gameServer);
		return id;
	}

	@Override
	public Long update(GameServer gameServer) throws ValidationException {
		gameServerValidator.validateUpdate(gameServer);
		Long id = gameServerDao.updateGameServer(gameServer);
		return id;
	}

	@Override
	public GameServer get(Long id) {
		return gameServerDao.get(id);
	}

	@Override
	public void search(SearchBean searchBean) {
		gameServerDao.search(searchBean);
	}

}
