package com.SkyDoc.demo.dto;

import com.SkyDoc.demo.entity.User.Role;

public class UserDTO {

    private Long id;
    private String employeeName;
    private String employeeId;
    private String username;
    private String password;  // optional (used only when creating/updating)
    private String email;
    private Role role;
    private boolean locked;   // ✅ represents account lock status

    // ✅ Default constructor (required by Jackson)
    public UserDTO() {}

    // ✅ Full constructor (used by controller)
    public UserDTO(Long id, String employeeName, String employeeId, String username,
                   String email, Role role, boolean locked) {
        this.id = id;
        this.employeeName = employeeName;
        this.employeeId = employeeId;
        this.username = username;
        this.email = email;
        this.role = role;
        this.locked = locked;
    }

    // ✅ Legacy constructor (optional, keeps backward compatibility)
    public UserDTO(Long id, String employeeName, String employeeId, String username,
                   String email, Role role) {
        this(id, employeeName, employeeId, username, email, role, false);
    }

    // ✅ Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public boolean isLocked() { return locked; }
    public void setLocked(boolean locked) { this.locked = locked; }
}
