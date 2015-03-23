package com.cf.fuer.manager;

import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.model.GameServer;
import com.cf.fuer.webapp.bean.SearchBean;

public interface IGameServerManager {

	Long create(GameServer gameServer) throws ValidationException;

	Long update(GameServer gameServer) throws ValidationException;

	GameServer get(Long id);

	void search(SearchBean searchBean);

}
