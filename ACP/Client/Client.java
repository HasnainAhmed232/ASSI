package ACP.Client;

import ACP.Employee.Employee;
import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {
    private static final String FILE_NAME = "EmpDB.dat";
    private static Employee[] employees = new Employee[50];
    private static int count = 0;

    public static void main(String[] args) {
        loadData();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        while (true) {
            String choice = JOptionPane.showInputDialog(
                    "1. Add New Employee\n" +
                    "2. Update Employee Info\n" +
                    "3. Delete Employee\n" +
                    "4. Search Employee\n" +
                    "5. Exit\n\nEnter your choice:"
            );

            if (choice == null) break;

            switch (choice) {
                case "1":
                    addEmployee(sdf);
                    break;
                case "2":
                    updateEmployee();
                    break;
                case "3":
                    deleteEmployee();
                    break;
                case "4":
                    searchEmployee();
                    break;
                case "5":
                    saveData();
                    JOptionPane.showMessageDialog(null, "Data saved successfully!");
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice");
            }
        }
    }

    private static void addEmployee(SimpleDateFormat sdf) {
        if (count >= 50) {
            JOptionPane.showMessageDialog(null, "Maximum employee limit reached");
            return;
        }

        try {
            String name = JOptionPane.showInputDialog("Enter Employee Name:");
            String father = JOptionPane.showInputDialog("Enter Father Name:");
            String job = JOptionPane.showInputDialog("Enter Job Category (Teacher/Officer/Staff/Labour):");
            String edu = JOptionPane.showInputDialog("Enter Education (Matric/FSc/BS/MS/PhD):");
            String nic = JOptionPane.showInputDialog("Enter NIC:");
            String dobStr = JOptionPane.showInputDialog("Enter Date of Birth (yyyy-MM-dd):");
            Date dob = sdf.parse(dobStr);
            int pay = Integer.parseInt(JOptionPane.showInputDialog("Enter Pay Scale:"));

            if (!validate(job, edu, pay)) {
                JOptionPane.showMessageDialog(null, "Invalid education or pay scale for " + job);
                return;
            }

            Employee e = new Employee();
            e.setEmpInformation(name, father, job, edu, nic, pay, dob);
            employees[count++] = e;
            JOptionPane.showMessageDialog(null, "Employee added successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private static void updateEmployee() {
        int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Employee ID to update:"));
        for (int i = 0; i < count; i++) {
            Employee e = employees[i];
            if (e != null && e.getEmpID() == id) {
                String newEdu = JOptionPane.showInputDialog("Enter New Education:");
                int newScale = Integer.parseInt(JOptionPane.showInputDialog("Enter New Pay Scale:"));
                String newJob = JOptionPane.showInputDialog("Enter New Job Category:");

                if (!validate(newJob, newEdu, newScale)) {
                    JOptionPane.showMessageDialog(null, "Invalid combination");
                    return;
                }

                e.UpdateEmpInformation(newEdu, newScale, newJob);
                JOptionPane.showMessageDialog(null, "Employee updated successfully");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Employee not found");
    }

    private static void deleteEmployee() {
        int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Employee ID to delete:"));
        for (int i = 0; i < count; i++) {
            Employee e = employees[i];
            if (e != null && e.getEmpID() == id) {
                e.DeleteEmpInformation();
                JOptionPane.showMessageDialog(null, "Employee deleted successfully");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Employee not found");
    }

    private static void searchEmployee() {
        String opt = JOptionPane.showInputDialog(
                "Search By:\n1. Emp ID\n2. Name\n3. Age\n4. Job Category"
        );

        switch (opt) {
            case "1":
                int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Employee ID:"));
                for (Employee e : employees) {
                    if (e != null && e.getEmpID() == id) {
                        JOptionPane.showMessageDialog(null, e.getEmployeeInfo());
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "Not found");
                break;

            case "2":
                String name = JOptionPane.showInputDialog("Enter Employee Name:");
                for (Employee e : employees) {
                    if (e != null && e.getEmployeeName().equalsIgnoreCase(name)) {
                        JOptionPane.showMessageDialog(null, e.getEmployeeInfo());
                    }
                }
                break;

            case "3":
                int age = Integer.parseInt(JOptionPane.showInputDialog("Enter Age:"));
                for (Employee e : employees) {
                    if (e != null && e.getDateOfBirth() != null) {
                        int empAge = (int) ((new Date().getTime() - e.getDateOfBirth().getTime()) / (1000L * 60 * 60 * 24 * 365));
                        if (empAge == age)
                            JOptionPane.showMessageDialog(null, e.getEmployeeInfo());
                    }
                }
                break;

            case "4":
                String job = JOptionPane.showInputDialog("Enter Job Category:");
                for (Employee e : employees) {
                    if (e != null && e.getJobCategory().equalsIgnoreCase(job)) {
                        JOptionPane.showMessageDialog(null, e.getEmployeeInfo());
                    }
                }
                break;

            default:
                JOptionPane.showMessageDialog(null, "Invalid choice");
        }
    }

    private static boolean validate(String job, String edu, int scale) {
        job = job.toLowerCase();
        edu = edu.toLowerCase();

        switch (job) {
            case "teacher":
                return (edu.equals("ms") || edu.equals("phd")) && scale >= 18;
            case "officer":
                return (edu.equals("bs") || edu.equals("ms") || edu.equals("phd")) && scale >= 17;
            case "staff":
                return (edu.equals("fsc") || edu.equals("bs") || edu.equals("ms") || edu.equals("phd")) && scale >= 11 && scale <= 16;
            case "labour":
                return (edu.equals("matric") || edu.equals("fsc") || edu.equals("bs") || edu.equals("ms") || edu.equals("phd")) && scale >= 1 && scale <= 10;
            default:
                return false;
        }
    }

    private static void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(employees);
            out.writeInt(count);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error saving: " + e.getMessage());
        }
    }

    private static void loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            employees = (Employee[]) in.readObject();
            count = in.readInt();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading: " + e.getMessage());
        }
    }
}
