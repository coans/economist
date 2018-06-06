package com.economist.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.economist.model.ResetPassword;
import com.economist.util.ValidatorUtil;

@Component
public class ResetPasswordValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return ResetPassword.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ResetPassword resetPassword = (ResetPassword) target;
		String password = resetPassword.getPassword();
		String repassword = resetPassword.getRepassword();
		// validate password
		if (StringUtils.isBlank(password)) {
			errors.rejectValue("password", "error.notempty");
		} else if (password.length() < 6 || password.length() > 100) {
			errors.rejectValue("password", "error.length", new Integer[]{6, 25}, ValidatorUtil.DEFAULT_MESSAGE);
		}
		// validate repassword
		if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(repassword) && !password.equals(repassword)) {
			errors.rejectValue("repassword", "error.passwords.notequal");
		} else if (StringUtils.isBlank(repassword)) {
			errors.rejectValue("repassword", "error.notempty");
		}
	}

}
