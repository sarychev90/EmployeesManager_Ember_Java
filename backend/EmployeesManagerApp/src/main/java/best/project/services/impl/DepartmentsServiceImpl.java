package best.project.services.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import best.project.dao.DepartmentsRepository;
import best.project.exeptions.NoEntityFoundException;
import best.project.models.Department;
import best.project.services.DepartmentsService;

/**
 * Implementation of the departments service
 */
@Service
public class DepartmentsServiceImpl implements DepartmentsService {

	private static final Logger LOGGER = Logger.getLogger(DepartmentsServiceImpl.class.getName());

	private DepartmentsRepository departmentsRepository;

	@Autowired
	public DepartmentsServiceImpl(DepartmentsRepository departmentsRepository) {
		this.departmentsRepository = departmentsRepository;
	}

	@Override
	public Department findById(Long id) throws NoEntityFoundException {
		LOGGER.log(Level.INFO, "Trying to find department by id: {0}", id);
		Department department = departmentsRepository.getOne(id);
		if (department == null) {
			LOGGER.log(Level.SEVERE, "Could not find department by id: {0}", id);
			throw new NoEntityFoundException("Could not find department by id: " + id);
		}
		return department;
	}

	@Override
	public Department findByEmployeeId(Long employeeId) throws NoEntityFoundException {
		LOGGER.log(Level.INFO, "Trying to find department by employee id: {0}", employeeId);
		Department department = departmentsRepository.findByEmployeeId(employeeId);
		if (department == null) {
			LOGGER.log(Level.SEVERE, "Could not find department by employee id: {0}", employeeId);
			throw new NoEntityFoundException("Could not find department by employee id: " + employeeId);
		}
		return department;
	}

	@Override
	public List<Department> findAll() throws NoEntityFoundException {
		LOGGER.log(Level.INFO, "Trying to get all departments from DB");
		List<Department> departments = departmentsRepository.findAll();
		if (departments == null || departments.isEmpty()) {
			LOGGER.log(Level.SEVERE, "Could not find departments in DB");
			throw new NoEntityFoundException("Could not find departments in DB");
		}
		return departments;
	}

	@Override
	public Department update(Department department) throws NoEntityFoundException {
		LOGGER.log(Level.INFO, "Try to update department data in DB with id: {0}", department.getId());
		Department departmentForUpdate = departmentsRepository.getOne(department.getId());
		if (departmentForUpdate == null) {
			LOGGER.log(Level.SEVERE, "Could not find department by id: {0}", department.getId());
			throw new NoEntityFoundException("Could not find department by id: " + department.getId());
		}
		return departmentsRepository.save(department);
	}

	@Override
	public Department save(Department department) {
		LOGGER.log(Level.INFO, "Try to save new department in DB");
		return departmentsRepository.save(department);
	}

	@Override
	public void delete(Long id) throws NoEntityFoundException {
		LOGGER.log(Level.INFO, "Trying to delete department by id: {0}", id);
		Department department = departmentsRepository.getOne(id);
		if (department == null) {
			LOGGER.log(Level.SEVERE, "Could not find department by id: {0}", id);
			throw new NoEntityFoundException("Could not find department by id: " + id);
		} 
		departmentsRepository.delete(department);
	}

}
