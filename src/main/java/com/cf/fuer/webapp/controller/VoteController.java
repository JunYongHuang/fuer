package com.cf.fuer.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cf.fuer.manager.IVoteManager;

@Controller
public class VoteController {
	
	@Autowired
	private IVoteManager voteManager;

    @RequestMapping("/vote.php")
	public String vote(HttpServletRequest request) {
    	request.setAttribute("voteItems", voteManager.allVoteItems());
		return "vote/vote";
	}    
	
    @RequestMapping("/voteExplain.php")
	public String explain() {
		return "vote/voteExplain";
	}    
    
    @RequestMapping("/voteDetail.php")
	public String detail(HttpServletRequest request) {
    	request.setAttribute("voteItems", voteManager.allVoteItems());
    	request.setAttribute("totalVote", voteManager.totalVote());
		return "vote/voteDetail";
	}  
}
