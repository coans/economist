package com.thera.model.enums;

public enum EnumGender {

	FEMALE("Female"), MALE("Male");

	private final String displayName;

	EnumGender(final String display) {
		this.displayName = display;
	}
	
    public String getDisplayName() {  
        return displayName;  
    }

	@Override
	public String toString() {
		return this.displayName;
	}

}