package com.cf.fuer.validator;

import org.springframework.stereotype.Component;

import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.gc.ConnectUtil;
import com.cf.fuer.gc.GCResult;
import com.cf.fuer.gc.Message;
import com.cf.fuer.gc.MessageConstants;
import com.cf.fuer.model.Charge;
import com.cf.fuer.util.GameServerUtils;

@Component
public class ChargeValidator extends BaseValidator{

	/**
	 * 验证创建
	 */
	public void validateCreate(Charge charge) throws ValidationException {
		String code = charge.getGameServer();
		if(isBlank(code)){
			throw new ValidationException("gameServer", "请选择要充值的服务器!");
		}
		String chargeUser = charge.getUser();
		if(isBlank(chargeUser)){
			throw new ValidationException("user", "请填写要充值的账号!");
		}
		if(!chargeUser.equals(charge.getConfirmUser())){
			throw new ValidationException("confirmUser", "两次账号输入不一致!");
		}
		//本地数据库验证用户是否在服务器上创建过角色
		if(!GameServerUtils.isRegInServer(code, chargeUser)){
			//游戏服务器验证
			if(ConnectUtil.canSend(code)){//目标地址可以连接,发送登陆消息
				//请求ID,充值用户名+当前时间,保证唯一
				String requestId = chargeUser + System.currentTimeMillis();
				String checkRoleMsg = Message.newCheckRoleRequest(code, chargeUser, requestId);
				ConnectUtil.sendMsg(checkRoleMsg);
				Message resultMessage = GCResult.getInstance().getResult(requestId);
				//发送之后等待结果
				long waitTime = 0;
				while(resultMessage == null){
					try {
						Thread.sleep(100);//睡眠等待结果
						resultMessage = GCResult.getInstance().getResult(requestId);
					} catch (InterruptedException e) {
						//
					}
					waitTime = waitTime + 100;
					if(waitTime >= MessageConstants.TIMEOUT){//
						break;
					}
				}
				if(resultMessage == null){//等待超时
					throw new ValidationException("gameServer", "充值的服务器暂时无法连接,请稍后重试或充值到其它服务器!");
				}else{
					//清除结果
					GCResult.getInstance().removeResult(requestId);
					
					if(resultMessage.isOkResult() && !resultMessage.isCreateRole()){
						//未创建角色
						throw new ValidationException("confirmUser", "该账号在充值的服务器上未创建角色,请创建角色后再进行充值!");					
					}else{//发生错误,或404
						//throw new ValidationException("confirmUser", "充值的服务器暂时无法连接,请稍后重试或充值到其它服务器!");
					}
					
				}
			}
			
		}
		
		long amount = charge.getChargeAmount();
		if(amount == 0){
			throw new ValidationException("chargeAmount", "请选择充值金额!");
		}
		if(amount == -1 && (charge.getOtherChargeAmount() == null || charge.getOtherChargeAmount() <= 0)){
			throw new ValidationException("chargeAmount", "请输入正确的充值金额!");
		}
//		if(amount % 100 != 0){
//			throw new ValidationException("chargeAmount", "充值金额只能为整数!");
//		}
		if(amount == -1){//验证通过后,如果是用户自己输入的金额,设置为订单金额
			charge.setChargeAmount(charge.getOtherChargeAmount() * 100);
		}
	}
}
