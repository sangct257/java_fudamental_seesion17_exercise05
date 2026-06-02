package org.example;


import org.example.dao.Management;
import org.example.entity.Assignment;
import org.example.entity.Employee;
import org.example.entity.Project;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Management mgmt = new Management();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n======= HỆ THỐNG QUẢN LÝ NHÂN VIÊN & DỰ ÁN =======");
            System.out.println("1. Thêm nhân viên mới");
            System.out.println("2. Thêm dự án mới");
            System.out.println("3. Gán nhân viên vào dự án");
            System.out.println("4. Cập nhật lương nhân viên");
            System.out.println("5. Hiển thị danh sách nhân viên & dự án tham gia");
            System.out.println("6. Thoát");
            System.out.print("Mời bạn chọn chức năng (1-6): ");

            int choice = inputInt();

            switch (choice) {
                case 1: handleAddEmployee(); break;
                case 2: handleAddProject(); break;
                case 3: handleAssign(); break;
                case 4: handleUpdateSalary(); break;
                case 5: handleListAll(); break;
                case 6:
                    System.out.println("Đã thoát hệ thống thành công!");
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng nhập lại số từ 1 đến 6.");
            }
        }
    }

    private static int inputInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Lỗi: Định dạng phải là số nguyên! Nhập lại: ");
            }
        }
    }

    private static double inputDouble() {
        while (true) {
            try {
                double val = Double.parseDouble(scanner.nextLine().trim());
                if (val < 0) {
                    System.out.print("Lỗi: Giá trị số tiền không được âm! Nhập lại: ");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.print("Lỗi: Định dạng phải là số thực! Nhập lại: ");
            }
        }
    }

    private static String inputString(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Lỗi: Không được bỏ trống trường này!");
            } else {
                return input;
            }
        }
    }


    private static void handleAddEmployee() {
        System.out.println("\n--- THÊM NHÂN VIÊN MỚI ---");
        String name = inputString("Nhập tên nhân viên: ");
        String dept = inputString("Nhập phòng ban: ");
        System.out.print("Nhập mức lương: ");
        double salary = inputDouble();

        Employee emp = new Employee(name, dept, salary);
        if (mgmt.addEmployee(emp)) {
            System.out.println("Thành công: Đã lưu thông tin nhân viên!");
        }
    }

    private static void handleAddProject() {
        System.out.println("\n--- THÊM DỰ ÁN MỚI ---");
        String name = inputString("Nhập tên dự án: ");
        System.out.print("Nhập ngân sách dự án (Budget): ");
        double budget = inputDouble();

        Project proj = new Project(name, budget);
        if (mgmt.addProject(proj)) {
            System.out.println("🎉 Thành công: Đã khởi tạo dự án mới!");
        }
    }

    private static void handleAssign() {
        System.out.println("\n--- PHÂN CÔNG NHÂN VIÊN VÀO DỰ ÁN ---");
        System.out.print("Nhập mã ID nhân viên: ");
        int empId = inputInt();
        System.out.print("Nhập mã ID dự án: ");
        int projId = inputInt();
        String role = inputString("Nhập vai trò trong dự án (Ví dụ: Leader, Developer, Tester): ");

        if (mgmt.assignEmployeeToProject(empId, projId, role)) {
            System.out.println("Thành công: Đã phân công nhân sự làm dự án!");
        }
    }

    private static void handleUpdateSalary() {
        System.out.println("\n--- CẬP NHẬT LƯƠNG NHÂN VIÊN ---");
        System.out.print("Nhập mã ID nhân viên: ");
        int empId = inputInt();
        System.out.print("Nhập mức lương mới: ");
        double newSalary = inputDouble();

        if (mgmt.updateEmployeeSalary(empId, newSalary)) {
            System.out.println("Thành công: Đã điều chỉnh bảng lương mới!");
        }
    }

    private static void handleListAll() {
        System.out.println("\n--- DANH SÁCH PHÂN CÔNG NHÂN SỰ & DỰ ÁN ---");
        List<Assignment> list = mgmt.listEmployeesAndProjects();

        if (list.isEmpty()) {
            System.out.println("ℹChưa có dữ liệu phân công dự án nào trong CSDL.");
            return;
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-7s | %-20s | %-15s | %-25s | %-15s\n",
                "MÃ NV", "TÊN NHÂN VIÊN", "PHÒNG BAN", "DỰ ÁN THAM GIA", "VAI TRÒ (ROLE)");
        System.out.println("----------------------------------------------------------------------------------------------------------------------");

        for (Assignment a : list) {
            System.out.printf("%-7d | %-20s | %-15s | %-25s | %-15s\n",
                    a.getEmployeeId().getId(),
                    a.getEmployeeId().getName(),
                    a.getEmployeeId().getDepartment(),
                    a.getProjectId().getName(),
                    a.getRole()
            );
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------");
    }
}