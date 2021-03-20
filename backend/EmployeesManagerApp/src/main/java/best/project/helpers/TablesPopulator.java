package best.project.helpers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import best.project.models.Department;
import best.project.models.Employee;
import best.project.repositories.DepartmentsRepository;
import best.project.repositories.EmployeesRepository;

public class TablesPopulator {

	private static final Logger LOGGER = Logger.getLogger(TablesPopulator.class.getName());

	private TablesPopulator() {
	}

	public static void loadInitialDataToDB(DepartmentsRepository departmentsRepository,
			EmployeesRepository employeesRepository) {
		try {
			
			if (departmentsRepository.findAll().isEmpty()) {

				LOGGER.log(Level.INFO, "DB is empty, start population tables...");

				List<Department> departments = new ArrayList<>();
				List<Employee> employees = new ArrayList<>();

				Department financeDepartment = new Department();
				financeDepartment.setName("Financial department");
				departments.add(financeDepartment);

				Department legalDepartment = new Department();
				legalDepartment.setName("Legal department");
				departments.add(legalDepartment);
				departmentsRepository.saveAll(departments);

				Employee employeeF1 = new Employee();
				employeeF1.setFirstName("Maxim");
				employeeF1.setLastName("Gorshkov");
				employeeF1.setPatronymicName("Olegovich");
				employeeF1.setPhoneNumber("+380563459120");
				employeeF1.setEmailAddress("m.gorshkov@gmail.com");
				employeeF1.setPosition("CFO");
				employeeF1.setSalary(BigDecimal.valueOf(55000.00));
				employeeF1.setAge(45);
				employeeF1.setPhoto(ImageVSBase64Convertor.imageToBase64Convertor("employeeF1.jpg"));
				employeeF1.setDepartment(financeDepartment);
				employees.add(employeeF1);

				Employee employeeF2 = new Employee();
				employeeF2.setFirstName("Ivan");
				employeeF2.setLastName("Stechko");
				employeeF2.setPatronymicName("Andrienko");
				employeeF2.setPhoneNumber("+380745678932");
				employeeF2.setEmailAddress("i.stechko@gmail.com");
				employeeF2.setPosition("Financial controler");
				employeeF2.setSalary(BigDecimal.valueOf(32000.00));
				employeeF2.setAge(32);
				employeeF2.setPhoto(ImageVSBase64Convertor.imageToBase64Convertor("employeeF2.jpg"));
				employeeF2.setDepartment(financeDepartment);
				employees.add(employeeF2);

				Employee employeeF3 = new Employee();
				employeeF3.setFirstName("Larry");
				employeeF3.setLastName("Tomson");
				employeeF3.setPatronymicName("Konstantinovich");
				employeeF3.setPhoneNumber("+380573678234");
				employeeF3.setEmailAddress("l.tomson@gmail.com");
				employeeF3.setPosition("Financial analyst");
				employeeF3.setSalary(BigDecimal.valueOf(35000.00));
				employeeF3.setAge(36);
				employeeF3.setPhoto(ImageVSBase64Convertor.imageToBase64Convertor("employeeF3.jpg"));
				employeeF3.setDepartment(financeDepartment);
				employees.add(employeeF3);

				Employee employeeL1 = new Employee();
				employeeL1.setFirstName("Andrey");
				employeeL1.setLastName("Polonskiy");
				employeeL1.setPatronymicName("Bogdanovich");
				employeeL1.setPhoneNumber("+380648975635");
				employeeL1.setEmailAddress("a.polonskiy@gmail.com");
				employeeL1.setPosition("Junior associate");
				employeeL1.setSalary(BigDecimal.valueOf(28000.00));
				employeeL1.setAge(29);
				employeeL1.setPhoto(ImageVSBase64Convertor.imageToBase64Convertor("employeeL1.jpg"));
				employeeL1.setDepartment(legalDepartment);
				employees.add(employeeL1);

				Employee employeeL2 = new Employee();
				employeeL2.setFirstName("Anna");
				employeeL2.setLastName("Gvozdic");
				employeeL2.setPatronymicName("Igorevna");
				employeeL2.setPhoneNumber("+380745673902");
				employeeL2.setEmailAddress("a.gvozdic@gmail.com");
				employeeL2.setPosition("CLO");
				employeeL2.setSalary(BigDecimal.valueOf(40000.00));
				employeeL2.setAge(40);
				employeeL2.setPhoto(ImageVSBase64Convertor.imageToBase64Convertor("employeeL2.jpg"));
				employeeL2.setDepartment(legalDepartment);
				employees.add(employeeL2);

				employeesRepository.saveAll(employees);

				LOGGER.log(Level.INFO, "Tables population done.");
			} else {
				LOGGER.log(Level.INFO, "DB isn't empty. No population.");
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Problem with table population. Cause: " + e);
		}
	}
}
