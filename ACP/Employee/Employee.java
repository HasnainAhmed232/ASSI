package ACP.Employee;

import java.util.Date;

public class Employee {
    private static int nId = 9000;

    String EmployeeName, FatherName, JobCategory, Education, NIC;
    int EmpID, payScale;
    Date DateOfBirth;

    public Employee() {
        EmpID = nId++;
    }

    public void setEmpInformation(String EmployeeName, String FatherName, String JobCategory,
                                  String Education, String NIC, int payScale, Date DateOfBirth) {
        this.EmployeeName = EmployeeName;
        this.FatherName = FatherName;
        this.JobCategory = JobCategory;
        this.Education = Education;
        this.NIC = NIC;
        this.payScale = payScale;
        this.DateOfBirth = DateOfBirth;
    }

    public void UpdateEmpInformation(String Education, int payScale, String JobCategory) {
        this.JobCategory = JobCategory;
        this.Education = Education;
        this.payScale = payScale;
    }

    public void DeleteEmpInformation() {
        this.EmployeeName = null;
        this.FatherName = null;
        this.JobCategory = null;
        this.Education = null;
        this.NIC = null;
        this.payScale = 0;
        this.DateOfBirth = null;
    }
    public void SearchAndViewByEmpID(int EmpID) {
        if (this.EmpID == EmpID) {
            System.out.println(getEmployeeInfo());
        }}

    public void SearchAndViewByEmployeeName(String EmployeeName) {
        if (this.EmployeeName.equalsIgnoreCase(EmployeeName)) {
            System.out.println(getEmployeeInfo());
        }
    }
    public int getEmpID() {
        return EmpID;
    }
    public String getEmployeeName() {
        return EmployeeName;
    }
  public String getJobCategory() {
        return JobCategory;
    }
    public Date getDateOfBirth() {
        return DateOfBirth;
    }

    public String getEmployeeInfo() {
        return "Employee ID: " + this.EmpID + "\n" +
                "Employee Name: " + this.EmployeeName + "\n" +
                "Father Name: " + this.FatherName + "\n" +
                "Job Category: " + this.JobCategory + "\n" +
                "Education: " + this.Education + "\n" +
                "NIC: " + this.NIC + "\n" +
                "Pay Scale: " + this.payScale + "\n" +
                "Date of Birth: " + this.DateOfBirth + "\n";
   
            }



}



