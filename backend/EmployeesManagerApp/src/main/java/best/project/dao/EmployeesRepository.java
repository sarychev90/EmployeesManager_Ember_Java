package best.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import best.project.models.Employee;

public interface EmployeesRepository extends JpaRepository<Employee, Integer> {

	/**
	 * Find all Employee by last name
	 * 
	 * @param lastName - last name of employee
	 * @return - list of employees with a given last name in the search 
	 */
	List<Employee> findByLastName(String lastName);

	/**
	 * Find all Employee by age
	 * 
	 * @param age - age of employee
	 * @return - list of employees with a given age in the search 
	 */
	List<Employee> findByAge(int age);
}
