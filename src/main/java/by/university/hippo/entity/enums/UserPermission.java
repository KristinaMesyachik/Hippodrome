package by.university.hippo.entity.enums;

public enum UserPermission {
    INFO_READ("info:read"),
    INFO_WRITE("info:write"),
    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return this.permission;
    }

}
