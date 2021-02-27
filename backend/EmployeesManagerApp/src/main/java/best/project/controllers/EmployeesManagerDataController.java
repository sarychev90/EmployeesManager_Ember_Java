package best.project.controllers;

import java.util.ArrayList;
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

import best.project.dao.DepartmentsRepository;
import best.project.dao.EmployeesRepository;
import best.project.models.Department;
import best.project.models.Employee;
import io.swagger.annotations.ApiOperation;

@RestController
public class EmployeesManagerDataController {

	private static final Logger LOGGER = Logger.getLogger(EmployeesManagerDataController.class.getName());
	private static final String DB_DATA_PROCESSING_ERROR = " encountered problem with data processing in DB. Cause: ";
	
	@Autowired
	public EmployeesRepository employeesRepository;

	@Autowired
	public DepartmentsRepository departmentsRepository;

	@ApiOperation(value = "Search an employee with an ID")
	@GetMapping(path = "/api/employee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getEmployee(@PathVariable(name = "id") int id) {

		Employee employee = null;
		try {
			employee = employeesRepository.findById(id).orElse(null);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Еxecution of the method getEmployee" + DB_DATA_PROCESSING_ERROR + e);
		}
		return employee != null ? new ResponseEntity<>(employee, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "View a list of available employee")
	@GetMapping(path = "/api/employees", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Employee>> getAllEmployees() {

		List<Employee> employees = null;
		try {
			employees = employeesRepository.findAll();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Еxecution of the method getAllEmployees" + DB_DATA_PROCESSING_ERROR + e);
		}
		return employees != null && !employees.isEmpty() ? new ResponseEntity<>(employees, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Create new employee in DB")
	@PostMapping(path = "/api/employee/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createEmployee(@PathVariable(name = "id") int id, @RequestBody Employee employee) {
		Department department = null;
		try {
			department = departmentsRepository.findById(id).orElse(null);
			employee.setDepartment(department);
			if (department != null) {
				employeesRepository.save(employee);
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Еxecution of the method createEmployee" + DB_DATA_PROCESSING_ERROR + e);
		}
		return department != null ? new ResponseEntity<>(HttpStatus.CREATED)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Update employee data in DB")
	@PutMapping(path = "/api/employee", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateEmployee(@RequestBody Employee employee) {
		Department department = null;
		try {
			department = departmentsRepository.findByEmployeeId(employee.getId());
			employee.setDepartment(department);
			if (department != null) {
				employeesRepository.save(employee);
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Еxecution of the method updateEmployee" + DB_DATA_PROCESSING_ERROR + e);
		}
		return employee != null ? new ResponseEntity<>(HttpStatus.OK) 
				: new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
	}

	@ApiOperation(value = "Delete employee from DB")
	@DeleteMapping(path = "/api/employee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteEmployee(@PathVariable(name = "id") int id) {
		ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		try {
			Employee employee = employeesRepository.findById(id).orElse(null);
			if (employee != null) {
				employeesRepository.delete(employee);
				responseEntity = new ResponseEntity<>(HttpStatus.OK);
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Еxecution of the method deleteEmployee" + DB_DATA_PROCESSING_ERROR + e);
		}
		return responseEntity;
	}

	@ApiOperation(value = "Search a department with an ID")
	@GetMapping(path = "/api/department/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getDepartment(@PathVariable(name = "id") int id) {
		Department department = null;
		try {
			department = departmentsRepository.findById(id).orElse(null);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Еxecution of the method getDepartment" + DB_DATA_PROCESSING_ERROR + e);
		}
		return department != null ? new ResponseEntity<>(department, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "View a list of available departments")
	@GetMapping(path = "/api/departments", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Department>> getAllDepartments() {
		List<Department> departments = null;
		try {
			departments = departmentsRepository.findAll();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Еxecution of the method getAllDepartments" + DB_DATA_PROCESSING_ERROR + e);
		}
		return departments != null && !departments.isEmpty() ? new ResponseEntity<>(departments, HttpStatus.OK)
				: new ResponseEntity<>(new ArrayList<Department>(),HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Create new department in DB")
	@PostMapping(path = "/api/department", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createDepartment(@RequestBody Department department) {
		ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.CREATED); 
		try {
			departmentsRepository.save(department);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Еxecution of the method createDepartment" + DB_DATA_PROCESSING_ERROR + e);
			responseEntity = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}	
		return responseEntity;
	}

	@ApiOperation(value = "Update department data in DB")
	@PutMapping(path = "/api/department", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateDepartment(@RequestBody Department department) {
		ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.OK); 
		try {
			departmentsRepository.save(department);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Еxecution of the method updateDepartment" + DB_DATA_PROCESSING_ERROR + e);
			responseEntity = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}	
		return responseEntity;
	}

	@ApiOperation(value = "Delete department from DB")
	@DeleteMapping(path = "/api/department/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteDepartment(@PathVariable(name = "id") int id) {
		ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		try {
			Department department = departmentsRepository.findById(id).orElse(null);
			if (department != null) {
				departmentsRepository.delete(department);
				responseEntity = new ResponseEntity<>(HttpStatus.OK);
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Еxecution of the method deleteDepartment" + DB_DATA_PROCESSING_ERROR + e);
		}
		return responseEntity;
	}
}
