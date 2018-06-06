package com.economist.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.economist.db.entity.Post;

@Component
public class PostValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Post.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Post post = (Post) target;
		// validate title
		if (StringUtils.isBlank(post.getTitle())) {
			errors.rejectValue("title", "error.notempty");
		}
		// validate message
		if (StringUtils.isBlank(post.getMessage())) {
			errors.rejectValue("message", "error.notempty");
		}
	}
	
}
