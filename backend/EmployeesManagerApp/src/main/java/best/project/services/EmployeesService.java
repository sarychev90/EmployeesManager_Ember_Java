package best.project.services;

import java.util.List;

import best.project.exeptions.NoEntityFoundException;
import best.project.models.Employee;


/**
 * Service for employees data operation in DB
 */
public interface EmployeesService {

	/**
	 * Find employee in DB by id
	 * 
	 * @param id - id of the employee
	 * @return - employee with a given id in the search
	 * @throws NoEntityFoundException if no employee found by id 
	 */
	Employee findById(Long id) throws NoEntityFoundException;
	
	/**
	 * Find all employees by department id
	 * 
	 * @param id - id of the department
	 * @return - all employees with a given id of department in the search
	 * @throws NoEntityFoundException if no employees found by department id 
	 */
	List<Employee> findByDepartmentId(Long id) throws NoEntityFoundException;
	
	/**
	 * Find all employees in DB
	 * 
	 * @return - all available employees in DB
	 * @throws NoEntityFoundException if no employees found in DB 
	 */
	List<Employee> findAll() throws NoEntityFoundException;
	
	/**
	 * Update employee data
	 * 
	 * @param employee - new employee data for update
	 * @return - updated employee
	 * @throws NoEntityFoundException if no department found by employee id 
	 */
	Employee update(Employee employee) throws NoEntityFoundException;
	
	/**
	 * Create employee in DB
	 * 
	 * @param departmentId - id of the department
	 * @param employee - data for new employee
	 * @return - new created employee in DB
	 * @throws NoEntityFoundException if no department found by id 
	 */
	Employee save(Long departmentId, Employee employee) throws NoEntityFoundException;
	
	/**
	 * Delete employee data by id
	 * 
	 * @param id - id of the employee prepared for delete operation
	 * @throws NoEntityFoundException if no employees found by id 
	 */
	void delete(Long id) throws NoEntityFoundException;	
}
