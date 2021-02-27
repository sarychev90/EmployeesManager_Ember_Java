package best.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import best.project.models.Department;

public interface DepartmentsRepository extends JpaRepository<Department, Integer> {

	/**
	 * Find all Department by name
	 * 
	 * @param name - name of the department
	 * @return - list of departments with a given name in the search 
	 */
	List<Department> findByName(String name);
	
	
	/**
	 * Find Department by Employee id
	 * 
	 * @param id - id of the employee
	 * @return - department with a given Employee id in the search 
	 */
	Department findByEmployeeId(int id);
}
