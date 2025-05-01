// model/Employee.java
package model;

import java.time.LocalDateTime;

public class Employee {
    private String employeeId;
    private String fullName;
    private String username;
    private String password;
    private String role;
    private LocalDateTime createdTime;

    public Employee() {}

    public Employee(String employeeId, String fullName, String username, String password, String role, LocalDateTime createdTime) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdTime = createdTime;
    }

    // Getter và Setter
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDateTime getCreatedTime() { return createdTime; }
    public void setCreatedTime(LocalDateTime createdTime) { this.createdTime = createdTime; }
}