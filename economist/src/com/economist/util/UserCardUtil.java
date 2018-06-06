package com.economist.util;

import java.util.ArrayList;
import java.util.List;

import com.economist.model.Item;

public class UserCardUtil {

	private static List<Item> userCard = new ArrayList<>();
	
	public static List<Item> getUserCard(){
		return userCard;
	}
}
