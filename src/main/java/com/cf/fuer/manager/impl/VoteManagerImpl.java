package com.cf.fuer.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.fuer.dao.IVoteDao;
import com.cf.fuer.manager.IUserManager;
import com.cf.fuer.manager.IVoteManager;
import com.cf.fuer.model.VoteItem;
import com.cf.fuer.webapp.bean.SearchBean;

@Service
public class VoteManagerImpl implements IVoteManager {

	@Autowired
	private IVoteDao voteDao;

	@Autowired
	private IUserManager userManager;

	@Override
	public List<VoteItem> allVoteItems() {
		return voteDao.allVoteItems();
	}

	@Override
	public int totalVote() {
		return voteDao.totalVote();
	}

	/**
	 * 1.更新投票数 2.插入投票注册信息 3.插入投票IP 4.注册用户
	 */
	@Override
	public void voteWithReg(Long itemId, String name, String email, String mobile, String addr, String ip) {
		voteDao.updateVoteCount(itemId);
		voteDao.createVoteReg(name, email, mobile, addr);
		Long userId = userManager.createAfterVote(email, name);
		voteDao.createVoteIp(ip, userId);
	}

	@Override
	public String voteNoReg(Long itemId, String ip) {
		voteDao.updateVoteCount(itemId);
		voteDao.createVoteIp(ip, null);
		return "";
	}

	@Override
	public void search(SearchBean searchBean) {
		voteDao.search(searchBean);
	}

}
