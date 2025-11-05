package com.SkyDoc.demo.dto;

import com.SkyDoc.demo.entity.User.Role;

public class UserDTO {

    private Long id;
    private String employeeName;
    private String employeeId;
    private String username;
    private String password;  // plain password (optional for creation)
    private String email;
    private Role role;

    public UserDTO() {}

    public UserDTO(Long id, String employeeName, String employeeId, String username, String email, Role role) {
        this.id = id;
        this.employeeName = employeeName;
        this.employeeId = employeeId;
        this.username = username;
        this.email = email;
        this.role = role;
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
}
