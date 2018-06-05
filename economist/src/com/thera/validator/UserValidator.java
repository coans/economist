package com.thera.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.thera.db.entity.User;
import com.thera.db.repository.UserRepository;
import com.thera.model.enums.EnumGender;
import com.thera.util.ValidatorUtil;

@Component
public class UserValidator implements Validator {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		// validate email
		if (user.getId() == 0) {
			if (StringUtils.isBlank(user.getEmail())) {
				errors.rejectValue("email", "error.notempty");
			} else {
				if (user.getEmail().length() > 100) {
					errors.rejectValue("email", "error.maxlength", new Integer[]{100}, ValidatorUtil.DEFAULT_MESSAGE);
				} else if (!ValidatorUtil.validateEmail(user.getEmail())) {
					errors.rejectValue("email", "error.email");
				} else {
					// validate if already exists
					User userInDb = userRepository.findActiveByEmail(user.getEmail());
					if (userInDb != null) {
						errors.rejectValue("email", "error.email.exists");
					}
				}
			}
		}
		// validate password
		if (StringUtils.isBlank(user.getPassword())) {
			errors.rejectValue("password", "error.notempty");
		} else if (user.getPassword().length() < 6 || user.getPassword().length() > 100) {
			errors.rejectValue("password", "error.length", new Integer[]{6, 25}, ValidatorUtil.DEFAULT_MESSAGE);
		}
		// validate first name
		if (StringUtils.isBlank(user.getFirstName())) {
			errors.rejectValue("firstName", "error.notempty");
		} else if (user.getFirstName().length() > 100) {
			errors.rejectValue("firstName", "error.maxlength", new Integer[]{45}, ValidatorUtil.DEFAULT_MESSAGE);
		}
		// validate last name
		if (StringUtils.isBlank(user.getLastName())) {
			errors.rejectValue("lastName", "error.notempty");
		} else if (user.getLastName().length() > 100) {
			errors.rejectValue("lastName", "error.maxlength", new Integer[]{45}, ValidatorUtil.DEFAULT_MESSAGE);
		}
		// validate gender
		if (StringUtils.isBlank(user.getGender())) {
			errors.rejectValue("gender", "error.notempty");
		} else if (!EnumGender.FEMALE.toString().equals(user.getGender()) && !EnumGender.MALE.toString().equals(user.getGender())) {
			errors.rejectValue("gender", "error.notempty");
		}
	}

}
