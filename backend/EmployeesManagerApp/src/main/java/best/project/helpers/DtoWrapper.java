package best.project.helpers;

import java.util.logging.Level;
import java.util.logging.Logger;

import best.project.dto.DepartmentDto;
import best.project.dto.EmployeeDto;
import best.project.models.Department;
import best.project.models.Employee;

public class DtoWrapper {
	
	private static final Logger LOGGER = Logger.getLogger(DtoWrapper.class.getName());
	
	private DtoWrapper() {
	}
	
	public static DepartmentDto prepareDepartmentDto(Department department) {
		LOGGER.log(Level.INFO, "Preparing DTO for department with id: {0}", department.getId());
		DepartmentDto departmentDto = new DepartmentDto();
		departmentDto.setId(department.getId());
		departmentDto.setName(department.getName());
		departmentDto.setEmployee(department.getEmployee());
		return departmentDto;
	}
	
	public static EmployeeDto prepareEmployeeDto(Employee employee) {
		LOGGER.log(Level.INFO, "Preparing DTO for employee with id: {0}", employee.getId());
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setId(employee.getId());
		employeeDto.setFirstName(employee.getFirstName());
		employeeDto.setLastName(employee.getLastName());
		employeeDto.setPatronymicName(employee.getPatronymicName());
		employeeDto.setPosition(employee.getPosition());
		employeeDto.setPhoneNumber(employee.getPhoneNumber());
		employeeDto.setEmailAddress(employee.getEmailAddress());
		employeeDto.setAge(employee.getAge());
		employeeDto.setSalary(employee.getSalary());
		employeeDto.setPhoto(employee.getPhoto());
		return employeeDto;
	}
	
}
