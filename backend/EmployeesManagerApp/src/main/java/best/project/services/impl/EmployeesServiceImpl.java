package best.project.services.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import best.project.dao.DepartmentsRepository;
import best.project.dao.EmployeesRepository;
import best.project.exeptions.NoEntityFoundExeption;
import best.project.models.Department;
import best.project.models.Employee;
import best.project.services.EmployeesService;

/**
 * Implementation of the employees service
 */
@Service
public class EmployeesServiceImpl implements EmployeesService {

	private static final Logger LOGGER = Logger.getLogger(EmployeesServiceImpl.class.getName());

	private DepartmentsRepository departmentsRepository;
	private EmployeesRepository employeesRepository;

	@Autowired
	public EmployeesServiceImpl(DepartmentsRepository departmentsRepository, EmployeesRepository employeesRepository) {
		this.departmentsRepository = departmentsRepository;
		this.employeesRepository = employeesRepository;
	}

	@Override
	public Employee findById(Long id) throws NoEntityFoundExeption {
		LOGGER.log(Level.INFO, "Trying to find employee by id: {0}", id);
		Employee employee = employeesRepository.findById(id).orElse(null);
		if (employee == null) {
			LOGGER.log(Level.SEVERE, "Could not find employee by id: {0}", id);
			throw new NoEntityFoundExeption("Could not find employee by id: " + id);
		}
		return employee;
	}

	@Override
	public List<Employee> findByDepartmentId(Long departmentId) throws NoEntityFoundExeption {
		LOGGER.log(Level.INFO, "Try to get all employees from DB by department id: {0}", departmentId);
		Department department = departmentsRepository.findById(departmentId).orElse(null);
		if (department == null) {
			LOGGER.log(Level.SEVERE, "Could not find department by id: {0}", departmentId);
			throw new NoEntityFoundExeption("Could not find department by id: " + departmentId);
		}
		List<Employee> employees = department.getEmployee();
		if (employees == null || employees.isEmpty()) {
			LOGGER.log(Level.SEVERE, "Could not find employees in department with id: {0}", departmentId);
			throw new NoEntityFoundExeption("Could not find employees in department with id: " + departmentId);
		}
		return employees;
	}

	@Override
	public List<Employee> findAll() throws NoEntityFoundExeption {
		LOGGER.log(Level.INFO, "Trying to get all employees from DB");
		List<Employee> employees = employeesRepository.findAll();
		if (employees == null || employees.isEmpty()) {
			LOGGER.log(Level.SEVERE, "Could not find employees in DB");
			throw new NoEntityFoundExeption("Could not find employees in DB");
		}
		return employees;
	}

	@Override
	public Employee update(Employee employee) throws NoEntityFoundExeption {
		LOGGER.log(Level.INFO, "Try to update employee data in DB with id: {0}", employee.getId());
		Department department = departmentsRepository.findByEmployeeId(employee.getId());
		if (department == null) {
			LOGGER.log(Level.SEVERE, "Could not find department by employee id: {0}", employee.getId());
			throw new NoEntityFoundExeption("Could not find department by employee id: " + employee.getId());
		}
		employee.setDepartment(department);
		return employeesRepository.save(employee);
	}

	@Override
	public Employee save(Long departmentId, Employee employee) throws NoEntityFoundExeption {
		LOGGER.log(Level.INFO, "Try to save new employee in DB");
		Department department = departmentsRepository.findById(departmentId).orElse(null);
		if (department == null) {
			LOGGER.log(Level.SEVERE, "Could not find department by id: {0}", departmentId);
			throw new NoEntityFoundExeption("Could not find department by id: " + departmentId);
		}
		employee.setDepartment(department);
		return employeesRepository.save(employee);
	}

	@Override
	public void delete(Long id) throws NoEntityFoundExeption {
		LOGGER.log(Level.INFO, "Trying to delete employee by id: {0}", id);
		Employee employee = employeesRepository.findById(id).orElse(null);
		if (employee == null) {
			LOGGER.log(Level.SEVERE, "Could not find employee by id: {0}", id);
			throw new NoEntityFoundExeption("Could not find employee by id: " + id);
		}
		employeesRepository.delete(employee);
	}

}
