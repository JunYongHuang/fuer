package com.cf.fuer.dao;

import java.util.List;

import com.cf.fuer.model.VoteItem;
import com.cf.fuer.webapp.bean.SearchBean;

public interface IVoteDao {

	List<VoteItem> allVoteItems();

	int totalVote();

	/**
	 * 更新投票数
	 * 
	 * @param itemId
	 */
	void updateVoteCount(Long itemId);

	/**
	 * 插入投票注册
	 */
	Long createVoteReg(String name, String email, String mobile, String addr);

	/**
	 * 插入投票IP.
	 * 
	 * @param ip
	 *            IP地址
	 * @param uid
	 *            用户ID,可以为空
	 * @return id
	 */
	Long createVoteIp(String ip, Long uid);

	/**
	 * 搜索投票参与人员
	 */
	void search(SearchBean searchBean);

}
