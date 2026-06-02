package org.example.dao;

import org.example.db.DBUtility;
import org.example.entity.Assignment;
import org.example.entity.Employee;
import org.example.entity.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Management {
    public boolean isEmployeeNameExist(String name) {
        boolean flag = false;
        Connection con = DBUtility.openConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement("SELECT COUNT(*) FROM employee WHERE name = ?");
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) flag = rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DBUtility.closeConnection(rs, pstmt, con);
        }
        return flag;
    }

    public boolean isProjectNameExist(String name) {
        boolean flag = false;
        Connection con = DBUtility.openConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement("SELECT COUNT(*) FROM project WHERE name = ?");
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                flag = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            new RuntimeException(e);
        }
        finally {
            DBUtility.closeConnection(rs, pstmt, con);
        }
        return flag;
    }

    public boolean isEmployeeExist(int id) {
        boolean flag = false;
        Connection con = DBUtility.openConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement("SELECT COUNT(*) FROM employee WHERE id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) flag = rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DBUtility.closeConnection(rs, pstmt, con);
        }
        return flag;
    }

    public boolean isProjectExist(int id) {
        boolean flag = false;
        Connection con = DBUtility.openConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement("SELECT COUNT(*) FROM project WHERE id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) flag = rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DBUtility.closeConnection(rs, pstmt, con);
        }
        return flag;
    }

    public boolean isAssignmentExist(int empId, int projId) {
        boolean flag = false;
        Connection con = DBUtility.openConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement("SELECT COUNT(*) FROM assignment WHERE employee_id = ? AND project_id = ?");
            pstmt.setInt(1, empId);
            pstmt.setInt(2, projId);
            rs = pstmt.executeQuery();
            if (rs.next()) flag = rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DBUtility.closeConnection(rs, pstmt, con);
        }
        return flag;
    }

    public boolean addEmployee(Employee employee) {
        if (isEmployeeNameExist(employee.getName())) {
            System.out.println("Lỗi: Tên nhân viên [" + employee.getName() + "] đã tồn tại!");
            return false;
        }
        boolean flag = false;
        Connection con = DBUtility.openConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("INSERT INTO employee(name, department, salary) VALUES (?, ?, ?)");
            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getDepartment());
            pstmt.setDouble(3, employee.getSalary());
            pstmt.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DBUtility.closeConnection(null, pstmt, con);
        }
        return flag;
    }

    public boolean addProject(Project project) {
        if (isProjectNameExist(project.getName())) {
            System.out.println("Lỗi: Tên dự án [" + project.getName() + "] đã tồn tại!");
            return false;
        }
        boolean flag = false;
        Connection con = DBUtility.openConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("INSERT INTO project(name, budget) VALUES (?, ?)");
            pstmt.setString(1, project.getName());
            pstmt.setDouble(2, project.getBudget());
            pstmt.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DBUtility.closeConnection(null, pstmt, con);
        }
        return flag;
    }

    public boolean assignEmployeeToProject(int employeeId, int projectId, String role) {
        if (!isEmployeeExist(employeeId)) {
            System.out.println("Lỗi: Nhân viên mã ID = " + employeeId + " không tồn tại!");
            return false;
        }
        if (!isProjectExist(projectId)) {
            System.out.println("Lỗi: Dự án mã ID = " + projectId + " không tồn tại!");
            return false;
        }
        if (isAssignmentExist(employeeId, projectId)) {
            System.out.println("Lỗi: Nhân viên này đã được gán vào dự án này từ trước!");
            return false;
        }

        boolean flag = false;
        Connection con = DBUtility.openConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("INSERT INTO assignment(employee_id, project_id, role) VALUES (?, ?, ?)");
            pstmt.setInt(1, employeeId);
            pstmt.setInt(2, projectId);
            pstmt.setString(3, role);
            pstmt.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DBUtility.closeConnection(null, pstmt, con);
        }
        return flag;
    }

    public boolean updateEmployeeSalary(int employeeId, double newSalary) {
        if (!isEmployeeExist(employeeId)) {
            System.out.println("Lỗi: Không tìm thấy nhân viên ID = " + employeeId + " để cập nhật lương!");
            return false;
        }
        boolean flag = false;
        Connection con = DBUtility.openConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("UPDATE employee SET salary = ? WHERE id = ?");
            pstmt.setDouble(1, newSalary);
            pstmt.setInt(2, employeeId);
            pstmt.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DBUtility.closeConnection(null, pstmt, con);
        }
        return flag;
    }

    public List<Assignment> listEmployeesAndProjects() {
        List<Assignment> list = new ArrayList<>();
        Connection con = DBUtility.openConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            String sql = "SELECT e.id AS emp_id, e.name AS emp_name, e.department, e.salary, " +
                    "p.id AS proj_id, p.name AS proj_name, p.budget, " +
                    "a.role " +
                    "FROM assignment a " +
                    "INNER JOIN employee e ON a.employee_id = e.id " +
                    "INNER JOIN project p ON a.project_id = p.id " +
                    "ORDER BY e.id ASC";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Employee emp = new Employee(
                        rs.getInt("emp_id"),
                        rs.getString("emp_name"),
                        rs.getString("department"),
                        rs.getDouble("salary")
                );

                Project proj = new Project(
                        rs.getInt("proj_id"),
                        rs.getString("proj_name"),
                        rs.getDouble("budget")
                );

                Assignment asm = new Assignment(emp, proj, rs.getString("role"));
                list.add(asm);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        finally { DBUtility.closeConnection(rs, stmt, con); }
        return list;
    }
}
