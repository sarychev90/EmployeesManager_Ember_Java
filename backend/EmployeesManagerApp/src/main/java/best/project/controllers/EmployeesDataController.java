package best.project.controllers;

import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import best.project.dto.DepartmentDto;
import best.project.helpers.DtoWrapper;
import best.project.mappers.DepartmentMapper;
import best.project.mappers.EmployeeMapper;
import best.project.models.Employee;
import best.project.services.EmployeesService;
import io.swagger.annotations.ApiOperation;

/**
 * Controller for the employees API operations.
 */
@RestController
public class EmployeesDataController {

	private static final Logger LOGGER = Logger.getLogger(EmployeesDataController.class.getName());
	private static final String DB_DATA_PROCESSING_ERROR = " encountered problem with data processing in DB. Cause: ";

	private EmployeesService employeesService;

	@Autowired
	public EmployeesDataController(EmployeesService employeesService) {
		this.employeesService = employeesService;
	}

	/**
     * Method return employee data by its id.
     * 
     * @param id - id of the employee
     *
     * @return ResponseEntity<?> - employee data with status 200 OK or 404 if not found data
     */
	@ApiOperation(value = "Search an employee with an ID")
	@GetMapping(path = "/api/employee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getEmployee(@PathVariable(name = "id") Long id) {
		Employee employee = null;
		try {
			employee = employeesService.findById(id);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Еxecution of the method getEmployee" + DB_DATA_PROCESSING_ERROR + e.getMessage() + e);
		}
		return employee != null ? new ResponseEntity<>(EmployeeMapper.INSTANCE.employeeToEmployeeDto(employee), 
				HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
     * Method return all employee in DB.
     *
     * @return ResponseEntity<List<Employee>> - list of employees with status 200 OK or 404 if not found data
     */
	@ApiOperation(value = "View a list of available employee")
	@GetMapping(path = "/api/employees", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> employees = null;
		try {
			employees = employeesService.findAll();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Еxecution of the method getAllEmployees" + DB_DATA_PROCESSING_ERROR + e.getMessage() + e);
		}
		return employees != null ? new ResponseEntity<>(employees, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	/**
     * Method create new employee in DB.
     * 
     * @param id - id of the department
     * @param employee - data for new employee
     *
     * @return ResponseEntity<?> with status 201 if data saved or 304 if not modified
     */
	@ApiOperation(value = "Create new employee in DB")
	@PostMapping(path = "/api/employee/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createEmployee(@PathVariable(name = "id") Long id, @RequestBody Employee employee) {
		ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
		try {
			employeesService.save(id, employee);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Еxecution of the method createEmployee" + DB_DATA_PROCESSING_ERROR + e.getMessage() + e);
			responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}

	/**
     * Method update employee data in DB.
     * 
     * @param employee - new data for employee
     *
     * @return ResponseEntity<?> with status 200 OK if data saved or 304 if not modified
     */
	@ApiOperation(value = "Update employee data in DB")
	@PutMapping(path = "/api/employee", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateEmployee(@RequestBody Employee employee) {
		ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.OK);
		try {
			employeesService.update(employee);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Еxecution of the method updateEmployee" + DB_DATA_PROCESSING_ERROR + e.getMessage() + e);
			responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}

	/**
     * Method delete employee data in DB by its id.
     * 
     * @param id - id of the employee
     *
     * @return ResponseEntity<?> with status 200 OK if data deleted or 304 if not modified
     */
	@ApiOperation(value = "Delete employee from DB")
	@DeleteMapping(path = "/api/employee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteEmployee(@PathVariable(name = "id") Long id) {
		ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.OK);
		try {
			employeesService.delete(id);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Еxecution of the method deleteEmployee" + DB_DATA_PROCESSING_ERROR + e.getMessage() + e);
			responseEntity = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		return responseEntity;
	}

}
