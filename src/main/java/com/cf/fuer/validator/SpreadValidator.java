package com.cf.fuer.validator;

import org.springframework.stereotype.Component;

import com.cf.fuer.exception.ValidationException;

@Component
public class SpreadValidator extends BaseValidator {
	
	public void validateCreate(Long userId, String name, String addressCode) throws ValidationException{
		if (isBlank(name)) {
			throw new ValidationException("name", "推广人姓名不能为空");
		}
		if (!isValideLenthStr(name, 1, 90)) {
			throw new ValidationException("name", "推广人姓名不能超过90个字符");
		}
		if (isBlank(addressCode)) {
			throw new ValidationException("addressCode", "推广地址码不能为空");
		}
		if (!isValideLenthStr(addressCode, 1, 90)) {
			throw new ValidationException("addressCode", "推广地址码不能超过90个字符");
		}
		if(!notExist("spreaduser", "addressCode", addressCode)){
			throw new ValidationException("name", "推广地址码已存在,请更换一个地址码!");
		}
	}
	
	public void validateUpdate(Long userId, String addressCode) throws ValidationException{
		if (isBlank(addressCode)) {
			throw new ValidationException("addressCode", "推广地址码不能为空");
		}
		if (!isValideLenthStr(addressCode, 1, 90)) {
			throw new ValidationException("addressCode", "推广地址码不能超过90个字符");
		}
		if(!notExist("spreaduser", "addressCode", addressCode)){
			throw new ValidationException("name", "推广地址码已存在,请更换一个地址码!");
		}
	}

}
