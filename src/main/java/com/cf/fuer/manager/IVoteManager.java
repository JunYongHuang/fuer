package com.cf.fuer.manager;

import java.util.List;

import com.cf.fuer.model.VoteItem;
import com.cf.fuer.webapp.bean.SearchBean;

public interface IVoteManager {

	List<VoteItem> allVoteItems();

	/**
	 * 投票总数
	 */
	int totalVote();

	/**
	 * 投票带注册
	 */
	void voteWithReg(Long itemId, String name, String email, String mobile, String addr, String ip);
	
	/**
	 * 投票不带注册
	 * @return 
	 */
	String voteNoReg(Long itemId,String ip);

	/**
	 * 搜索投票参与人员
	 */
	void search(SearchBean searchBean);

}
