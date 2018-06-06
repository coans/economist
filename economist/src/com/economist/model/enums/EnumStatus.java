package com.economist.model.enums;

public enum EnumStatus {

	ACTIVE("Active"), INACTIVE("Inactive");

	private final String displayName;

	EnumStatus(final String display) {
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