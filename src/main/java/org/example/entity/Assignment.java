package org.example.entity;

public class Assignment {
    private Employee employeeId;
    private Project projectId;
    private String  role;

    public Assignment() {
    }

    public Assignment(Project projectId, String role) {
        this.projectId = projectId;
        this.role = role;
    }

    public Assignment(Employee employeeId, Project projectId, String role) {
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.role = role;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public Project getProjectId() {
        return projectId;
    }

    public void setProjectId(Project projectId) {
        this.projectId = projectId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "employeeId=" + employeeId +
                ", projectId=" + projectId +
                ", role='" + role + '\'' +
                '}';
    }
}
