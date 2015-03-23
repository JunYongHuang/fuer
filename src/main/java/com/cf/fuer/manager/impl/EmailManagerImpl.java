package com.cf.fuer.manager.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.cf.fuer.manager.IEmailManager;
import com.cf.fuer.util.MailEngine;

@Service
public class EmailManagerImpl implements IEmailManager{

	private final Log log = LogFactory.getLog(EmailManagerImpl.class);
	
	@Autowired
	private SimpleMailMessage mailMessage;

	@Autowired
	private MailEngine mailEngine;
	
	@Override
	public void sendEmail(String emailAddress, String subject, String content) {
		mailMessage.setTo(emailAddress);
		mailMessage.setSubject(subject);
		mailMessage.setText(content);
		mailEngine.send(mailMessage);
	}
	
	@Override
	public void sendSpreadEmail(final String to,final String presnetCode,final String username){
//		Thread sendEmail = new Thread("sendSpreadEmail"){
//			@Override
//			public void run() {
				
				try {
					String subject = "够快！够狠！够足球！够足球新服9月9日10:00火热开启!";
					String htmlContent = "<div style='text-align:center;'><table width='600' cellpadding='0' cellspacing='0' border='0' style='margin:0 auto;'><tbody><tr><td>" +
							"<div style='width:600px;text-align:left;font:12px/15px simsun;color:#000;background:#fff;'><div>" +
							"<a href='http://www.yyfuer.com/fzcEmail.php' title='够快！够狠！够足球！够足球新服火热开启。' target='_blank'>" +
							"<img src='http://www.yyfuer.com/images/fzc.jpg?v=3' width='600' height='339'/></a>" +
							"<div><div style='color:#2A3137;padding-left:16px;line-height:22px;margin-bottom:10px;'>" +
							"够足球、够精彩。够足球、够man。超强的实时计算比赛引擎，超炫的比赛画面，全拟真的足球元素，这就是2013最强足球游戏----够足球！够足球新服“梦寐以球”盛大开启！够足球邀您一起于2013年9月9日10:00开启您的足球圆梦之旅。众多玩家，八重大礼，巨浪来袭，惊喜奖品，不容错过!" +
							"	<br/>快速游戏通道：<a href='http://www.yyfuer.com/fzc.php' target='_blank'>http://www.yyfuer.com/fzcemail.php</a></div>" +
							"<div style='color:#2A3137;padding-left:16px;line-height:22px;margin-bottom:10px;'>作为够足球的老用户，我们特别为您准备了一个专属礼包，兑换码为：<b>"+presnetCode+"</b> 赶紧登陆游戏兑换吧！</div>" +
							"<div style='color:#2A3137;padding-left:16px;line-height:22px;margin-bottom:10px;'>温馨提醒：您在够足球的注册名为："+username+", 如果您忘记密码，请<a href='http://www.yyfuer.com/forget.php'>点击此处</a></div></div>" +
							" </td></tr></tbody></table></div>";
					mailEngine.sendHtmlMail(subject, to, htmlContent);
					log.warn("发送"+to+"成功！");
				} catch (Exception e) {
					log.error("推广邮件发送消息到"+to+"失败", e);
				}
//			}
//		};
//		sendEmail.start();
	}
	
	@Override
	public boolean finishEmail(final String email,final String name, final String password){
		try {
			String subject = "够足球—职业足球经理游戏-注册消息";
			String content = "您好，"+name+"\n您已成功完善够足球(www.yyfuer.com)账号的邮箱信息,同时你也可以通过此邮箱登陆游戏,用户名:"+ email +" 密码:"+password+",祝您游戏愉快！";
			content = content + "\n\n够足球官方QQ群:144474190\n\n够足球(www.yyfuer.com) Goal Football ------ A Game Born To Lead\n句多网络科技（上海）有限公司";
			sendEmail(email,subject,content);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 完成注册后发送消息(另开线程)到用户邮箱.
	 */
	@Override
	public void sendEmailAfterReg(final String email,final String username, final String password){
		Thread sendEmail = new Thread("sendEmail"){
			@Override
			public void run() {
				
				try {
					String subject = "够足球—职业足球经理游戏-注册消息";
					String content = "你好，\n欢迎进入够足球(www.yyfuer.com),您注册的用户名为:"+ username +" 忘记密码时,可通过本邮箱找回密码!";
					sendEmail(email,subject,content);
				} catch (Exception e) {
					log.warn("注册发送消息到"+email+"失败", e);
				}
			}
		};
		sendEmail.start();
	}
	/**
	 * 投票后发送消息(另开线程)到用户邮箱.
	 */
	@Override
	public void sendEmailAfterVote(final String email, final String password, final String name){
		Thread sendEmail = new Thread("sendEmail"){
			@Override
			public void run() {
				try {
					String subject = "够足球—职业足球经理游戏-投票反馈";
					String content = "你好，"+name+":\n感谢您参与足球豪门的投票活动，你将有机会获得大奖！同时你自动成为网站会员，用户名："+ email +" 初始密码为:"+password+"，欢迎关注，更加精彩的足球世界等着您！\n\n够足球 (www.yyfuer.com)Goal Football ------ A Game Born To Lead\n句多网络科技（上海）有限公司";
					sendEmail(email,subject,content);
				} catch (Exception e) {
					log.warn("投票后发送消息到"+email+"失败", e);
				}
			}
		};
		sendEmail.start();
	}
	/**
	 * 管理员为用户重置密码后发送消息(另开线程)到用户邮箱.
	 */
	@Override
	public void sendEmailAfterResetPwd(final String email, final String password, final String name){
		Thread sendEmail = new Thread("sendEmail"){
			@Override
			public void run() {
				try {
					String subject = "够足球—职业足球经理游戏-管理员重置密码";
					String content = "你好，"+name+":\n管理员已经为你重置密码,您的新密码为:"+password+"，请尽快登陆系统修改密码！\n\n够足球(www.yyfuer.com) Goal Football ------ A Game Born To Lead\n句多网络科技（上海）有限公司";
					sendEmail(email,subject,content);
				} catch (Exception e) {
					log.warn("重置密码后发送消息到"+email+"失败", e);
				}
			}
		};
		sendEmail.start();
	}
}
