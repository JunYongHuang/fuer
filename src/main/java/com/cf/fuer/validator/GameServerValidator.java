package com.cf.fuer.validator;

import org.springframework.stereotype.Component;

import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.model.GameServer;
import com.cf.fuer.util.GameServerUtils;

@Component
public class GameServerValidator extends BaseValidator{

	/**
	 * 验证创建
	 */
	public void validateCreate(GameServer gameServer) throws ValidationException {
//		if(isBlank(charge.getGameServer())){
//			throw new ValidationException("gameServer", "请选择要充值的服务器!");
//		}
//		String chargeUser = charge.getUser();
//		if(isBlank(chargeUser)){
//			throw new ValidationException("user", "请填写要充值的账号!");
//		}
//		if(!chargeUser.equals(charge.getConfirmUser())){
//			throw new ValidationException("confirmUser", "两次账号输入不一致!");
//		}
//		int amount = charge.getChargeAmount();
//		if(amount == 0 ){
//			throw new ValidationException("chargeAmount", "请选择充值金额!");
//		}
		
//		if(amount % 100 != 0){
//			throw new ValidationException("chargeAmount", "充值金额只能为整数!");
//		}
	}

	public void validateUpdate(GameServer gameServer) {
		
	}
}
