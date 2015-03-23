package com.cf.fuer.validator;

import org.springframework.stereotype.Component;

import com.cf.fuer.exception.ValidationException;

@Component
public class ChargeActivityValidator extends BaseValidator{
	
	public void valicateUpdate(long id, int rmbMoney, int goldMoney) throws ValidationException{
		valicateSave(rmbMoney, goldMoney);
		if(!notExist("chargeActivity", "rmbMoney", rmbMoney * 100, id)){
			throw new ValidationException("rmb", rmbMoney + "元的活动已存在!");
		}
	}
	
	private void valicateSave(int rmbMoney, int goldMoney) throws ValidationException{
		if(rmbMoney <= 0){
			throw new ValidationException("rmb", "人民币数额必须大于0!");
		}
		if(goldMoney <= 0){
			throw new ValidationException("gold", "金币数额必须大于0!");
		}
	}

	public void validateCreate(int rmbMoney, int goldMoney) throws ValidationException {
		valicateSave(rmbMoney, goldMoney);
		if(!notExist("chargeActivity", "rmbMoney", rmbMoney * 100)){
			throw new ValidationException("rmb", rmbMoney + "元的活动已存在!");
		}
		
	}
}
