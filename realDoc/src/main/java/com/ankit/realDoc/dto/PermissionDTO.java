package com.ankit.realDoc.dto;

public class PermissionDTO {
    
    private Long userId;
    private String permissionType;
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getPermissionType() {
        return permissionType;
    }
    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }

    
}
