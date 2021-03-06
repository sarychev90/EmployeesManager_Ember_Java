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

import best.project.models.Department;
import best.project.services.DepartmentsService;
import io.swagger.annotations.ApiOperation;

/**
 * Controller for the departments API operations.
 */
@RestController
public class DepartmentsDataController {

	private static final Logger LOGGER = Logger.getLogger(DepartmentsDataController.class.getName());
	private static final String DB_DATA_PROCESSING_ERROR = " encountered problem with data processing in DB. Cause: ";

	private DepartmentsService departmentsService;

	@Autowired
	public DepartmentsDataController(DepartmentsService departmentsService) {
		this.departmentsService = departmentsService;
	}
	
	/**
     * Method return department data by its id.
     * 
     * @param id - id of the department
     *
     * @return ResponseEntity<?> - department data with status 200 OK or 404 if not found data
     */
	@ApiOperation(value = "Search a department with an ID")
	@GetMapping(path = "/api/department/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getDepartment(@PathVariable(name = "id") Long id) {
		Department department = null;
		try {
			department = departmentsService.findById(id);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Еxecution of the method getDepartment" + DB_DATA_PROCESSING_ERROR + e.getMessage()+ e);
		}
		return department != null ? new ResponseEntity<>(department, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
     * Method return all departments in DB.
     *
     * @return ResponseEntity<List<Department>> - list of departments with status 200 OK or 404 if not found data
     */
	@ApiOperation(value = "View a list of available departments")
	@GetMapping(path = "/api/departments", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Department>> getAllDepartments() {
		List<Department> departments = null;
		try {
			departments = departmentsService.findAll();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Еxecution of the method getAllDepartments" + DB_DATA_PROCESSING_ERROR + e.getMessage()+ e);
		}
		return departments != null ? new ResponseEntity<>(departments, HttpStatus.OK)
				: new ResponseEntity<>(new ArrayList<Department>(), HttpStatus.NOT_FOUND);
	}

	/**
     * Method create new department in DB.
     * 
     * @param department - data for new department
     *
     * @return ResponseEntity<?> with status 201 if data saved or 304 if not modified
     */
	@ApiOperation(value = "Create new department in DB")
	@PostMapping(path = "/api/department", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createDepartment(@RequestBody Department department) {
		ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
		try {
			departmentsService.save(department);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Еxecution of the method createDepartment" + DB_DATA_PROCESSING_ERROR + e.getMessage()+ e);
			responseEntity = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		return responseEntity;
	}

	/**
     * Method update department data in DB.
     * 
     * @param department - new data for department
     *
     * @return ResponseEntity<?> with status 200 OK if data saved or 304 if not modified
     */
	@ApiOperation(value = "Update department data in DB")
	@PutMapping(path = "/api/department", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateDepartment(@RequestBody Department department) {
		ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.OK);
		try {
			departmentsService.save(department);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Еxecution of the method updateDepartment" + DB_DATA_PROCESSING_ERROR + e.getMessage()+ e);
			responseEntity = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		return responseEntity;
	}

	/**
     * Method delete department data in DB by its id.
     * 
     * @param id - id of the department
     *
     * @return ResponseEntity<?> with status 200 OK if data deleted or 304 if not modified
     */
	@ApiOperation(value = "Delete department from DB")
	@DeleteMapping(path = "/api/department/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteDepartment(@PathVariable(name = "id") Long id) {
		ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.OK);
		try {
			departmentsService.delete(id);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Еxecution of the method deleteDepartment" + DB_DATA_PROCESSING_ERROR + e.getMessage()+ e);
			responseEntity = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		return responseEntity;
	}
}
