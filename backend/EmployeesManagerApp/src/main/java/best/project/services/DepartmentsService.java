package best.project.services;

import java.util.List;

import best.project.exeptions.NoEntityFoundException;
import best.project.models.Department;


/**
 * Service for departments data operation in DB
 */
public interface DepartmentsService {
	
	/**
	 * Find department by id
	 * 
	 * @param id - id of the department
	 * @return - department with a given id in the search
	 * @throws NoEntityFoundException if no department found by id 
	 */
	Department findById(Long id) throws NoEntityFoundException;

	/**
	 * Find department by employee id
	 * 
	 * @param id - id of the employee
	 * @return - department with a given employee id in the search
	 * @throws NoEntityFoundException if no department found by employee id 
	 */
	Department findByEmployeeId(Long id) throws NoEntityFoundException;
	
	/**
	 * Find all departments in DB
	 * 
	 * @return - all available departments in DB
	 * @throws NoEntityFoundException if no departments found in DB 
	 */
	List<Department> findAll() throws NoEntityFoundException;
	
	/**
	 * Update department data by id
	 * 
	 * @param department - new department data for update
	 * @return - updated department
	 */
	Department update(Department department) throws NoEntityFoundException;
	
	/**
	 * Create new department in DB
	 * 
	 * @param department - data for new department
	 * @return - new created department in DB
	 */
	Department save(Department department);
	
	/**
	 * Delete department data by id
	 * 
	 * @param id - id of the department prepared for delete operation
	 * @throws NoEntityFoundException if no department found by id 
	 */
	void delete(Long id) throws NoEntityFoundException;
}
