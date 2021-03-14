package best.project.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import best.project.dao.DepartmentsRepository;
import best.project.dao.EmployeesRepository;
import best.project.exeptions.NoEntityFoundException;
import best.project.models.Department;
import best.project.models.Employee;
import best.project.services.impl.EmployeesServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EmployeesServiceImplTest {

	private static final Long DEPARTMENT_1_ID = 1l;
	private static final String DEPARTMENT_1_NAME = "Financial department";

	private static final Long EMPLOYEE_1_ID = 1l;
	private static final Long EMPLOYEE_2_ID = 2l;

	private static final String EMPLOYEE_1_FIRST_NAME = "Maxim";
	private static final String EMPLOYEE_2_FIRST_NAME = "Ivan";

	private static final int EMPLOYEE_1_AGE = 45;
	private static final int EMPLOYEE_2_AGE = 32;

	@Mock
	private DepartmentsRepository departmentsRepository;

	@Mock
	private EmployeesRepository employeesRepository;

	@InjectMocks
	private EmployeesServiceImpl employeesServiceImpl;

	@Test
	public void testFindById_employeeExists() throws Exception {
		// given
		Employee employeeF1 = new Employee();
		employeeF1.setId(EMPLOYEE_1_ID);
		employeeF1.setFirstName(EMPLOYEE_1_FIRST_NAME);
		employeeF1.setAge(EMPLOYEE_1_AGE);

		// when
		when(employeesRepository.getOne(anyLong())).thenReturn(employeeF1);

		// then
		Employee foundEmployee = employeesServiceImpl.findById(EMPLOYEE_1_ID);
		assertNotNull(foundEmployee, "Employee could not be null");
		assertEquals(EMPLOYEE_1_FIRST_NAME, foundEmployee.getFirstName(),
				String.format("Name of employee must be: %s", EMPLOYEE_1_FIRST_NAME));
		assertEquals(EMPLOYEE_1_AGE, foundEmployee.getAge(),
				String.format("Age of employee must be: %d", EMPLOYEE_1_AGE));
		verify(employeesRepository, times(1)).getOne(eq(EMPLOYEE_1_ID));
	}

	@Test
	public void testFindById_NoEntityFoundException() throws Exception {
		// when
		when(employeesRepository.getOne(anyLong())).thenReturn(null);

		// then
		Assertions.assertThrows(NoEntityFoundException.class, () -> {
			employeesServiceImpl.findById(EMPLOYEE_1_ID);
		});
		verify(employeesRepository, times(1)).getOne(eq(EMPLOYEE_1_ID));
	}

	@Test
	public void testFindByDepartmentId_employeesExists() throws Exception {
		// given
		Department financeDepartment = new Department();
		financeDepartment.setName(DEPARTMENT_1_NAME);
		financeDepartment.setId(DEPARTMENT_1_ID);

		List<Employee> employees = new ArrayList<>();

		Employee employeeF1 = new Employee();
		employeeF1.setId(EMPLOYEE_1_ID);
		employeeF1.setFirstName(EMPLOYEE_1_FIRST_NAME);
		employeeF1.setAge(EMPLOYEE_1_AGE);
		employees.add(employeeF1);

		Employee employeeF2 = new Employee();
		employeeF2.setId(EMPLOYEE_2_ID);
		employeeF2.setFirstName(EMPLOYEE_2_FIRST_NAME);
		employeeF2.setAge(EMPLOYEE_2_AGE);
		employees.add(employeeF2);

		financeDepartment.setEmployee(employees);

		// when
		when(departmentsRepository.getOne(anyLong())).thenReturn(financeDepartment);

		// then
		List<Employee> foundEmployees = employeesServiceImpl.findByDepartmentId(DEPARTMENT_1_ID);
		assertNotNull(foundEmployees, "List of employees could not be null");
		assertFalse(foundEmployees.isEmpty(), "List of employees could not be empty");
		assertEquals(2, foundEmployees.size(), "List of employees must conteins 2 employees");
		assertEquals(EMPLOYEE_1_FIRST_NAME, foundEmployees.get(0).getFirstName(),
				String.format("Name of the first employee must be: %s", EMPLOYEE_1_FIRST_NAME));
		assertEquals(EMPLOYEE_1_AGE, foundEmployees.get(0).getAge(),
				String.format("Age of the first employee must be: %d", EMPLOYEE_1_AGE));
		verify(departmentsRepository, times(1)).getOne(eq(DEPARTMENT_1_ID));
	}

	@Test
	public void testFindByDepartmentId_NoEntityFoundException() throws Exception {
		// when
		when(departmentsRepository.getOne(anyLong())).thenReturn(null);

		// then
		Assertions.assertThrows(NoEntityFoundException.class, () -> {
			employeesServiceImpl.findByDepartmentId(DEPARTMENT_1_ID);
		});
		verify(departmentsRepository, times(1)).getOne(eq(DEPARTMENT_1_ID));
	}
	
	@Test
	public void testFindAllEmployees_employeesExist() throws Exception {
		// given
		List<Employee> employees = new ArrayList<>();

		Employee employeeF1 = new Employee();
		employeeF1.setId(EMPLOYEE_1_ID);
		employeeF1.setFirstName(EMPLOYEE_1_FIRST_NAME);
		employeeF1.setAge(EMPLOYEE_1_AGE);
		employees.add(employeeF1);

		Employee employeeF2 = new Employee();
		employeeF2.setId(EMPLOYEE_2_ID);
		employeeF2.setFirstName(EMPLOYEE_2_FIRST_NAME);
		employeeF2.setAge(EMPLOYEE_2_AGE);
		employees.add(employeeF2);

		// when
		when(employeesRepository.findAll()).thenReturn(employees);
				
		// then
		List<Employee> foundEmployees = employeesServiceImpl.findAll();

		assertNotNull(foundEmployees, "List of employees could not be null");
		assertFalse(foundEmployees.isEmpty(), "List of employees could not be empty");
		assertEquals(2, foundEmployees.size(), "List of employees must conteins 2 employees");
		assertEquals(EMPLOYEE_1_FIRST_NAME, foundEmployees.get(0).getFirstName(),
				String.format("Name of the first employee must be: %s", EMPLOYEE_1_FIRST_NAME));
		assertEquals(EMPLOYEE_1_AGE, foundEmployees.get(0).getAge(),
				String.format("Age of the first employee must be: %d", EMPLOYEE_1_AGE));
		verify(employeesRepository, times(1)).findAll();
	}

	@Test
	public void testFindAllEmployees_NoEntityFoundException() throws Exception {
		// when
		when(employeesRepository.findAll()).thenReturn(null);

		// then
		Assertions.assertThrows(NoEntityFoundException.class, () -> {
			employeesServiceImpl.findAll();
		});
		verify(employeesRepository, times(1)).findAll();
	}
	
	@Test
	public void testUpdateEmployee_departmentExists() throws Exception {
		// given
		Department financeDepartmentForUpdate = new Department();
		financeDepartmentForUpdate.setName(DEPARTMENT_1_NAME);
		financeDepartmentForUpdate.setId(DEPARTMENT_1_ID);

		Employee employeeForUpdate = new Employee();
		employeeForUpdate.setId(EMPLOYEE_1_ID);
		employeeForUpdate.setFirstName(EMPLOYEE_1_FIRST_NAME);
		employeeForUpdate.setAge(EMPLOYEE_1_AGE);
		
		Employee employeeUpdated = new Employee();
		employeeUpdated.setId(EMPLOYEE_1_ID);
		employeeUpdated.setFirstName(EMPLOYEE_1_FIRST_NAME);
		employeeUpdated.setAge(EMPLOYEE_1_AGE);
		employeeUpdated.setDepartment(financeDepartmentForUpdate);
		
		// when
		when(departmentsRepository.findByEmployeeId(anyLong())).thenReturn(financeDepartmentForUpdate);
		when(employeesRepository.save(ArgumentMatchers.<Employee>any())).
				thenReturn(employeeUpdated);

		// then
		Employee employeeReturnUpdated = employeesServiceImpl.update(employeeForUpdate);
		
		assertNotNull(employeeReturnUpdated, "Employee could not be null");
		assertEquals(employeeForUpdate.getFirstName(), employeeReturnUpdated.getFirstName(),
				String.format("First name of employee must be: %s", employeeForUpdate.getFirstName()));
		verify(departmentsRepository, times(1)).findByEmployeeId(eq(EMPLOYEE_1_ID));
		verify(employeesRepository, times(1)).save(eq(employeeForUpdate));
	}

	@Test
	public void testUpdateEmployee_NoEntityFoundException() throws Exception {
		// given
		Employee employeeForUpdate = new Employee();
		employeeForUpdate.setId(EMPLOYEE_1_ID);
		employeeForUpdate.setFirstName(EMPLOYEE_1_FIRST_NAME);
		employeeForUpdate.setAge(EMPLOYEE_1_AGE);
		
		// when
		when(departmentsRepository.findByEmployeeId(anyLong())).thenReturn(null);

		// then
		Assertions.assertThrows(NoEntityFoundException.class, () -> {
			employeesServiceImpl.update(employeeForUpdate);
		});
		verify(departmentsRepository, times(1)).findByEmployeeId(eq(EMPLOYEE_1_ID));
	}
	
	@Test
	public void testSaveEmployee_departmentExists() throws Exception {
		// given
		Department financeDepartmentForSave = new Department();
		financeDepartmentForSave.setName(DEPARTMENT_1_NAME);
		financeDepartmentForSave.setId(DEPARTMENT_1_ID);

		Employee employeeForSave = new Employee();
		employeeForSave.setId(EMPLOYEE_1_ID);
		employeeForSave.setFirstName(EMPLOYEE_1_FIRST_NAME);
		employeeForSave.setAge(EMPLOYEE_1_AGE);
		
		Employee employeeSaved = new Employee();
		employeeSaved.setId(EMPLOYEE_1_ID);
		employeeSaved.setFirstName(EMPLOYEE_1_FIRST_NAME);
		employeeSaved.setAge(EMPLOYEE_1_AGE);
		employeeSaved.setDepartment(financeDepartmentForSave);
		
		// when
		when(departmentsRepository.getOne(anyLong())).thenReturn(financeDepartmentForSave);
		when(employeesRepository.save(ArgumentMatchers.<Employee>any())).
				thenReturn(employeeSaved);

		// then
		Employee employeeReturnSaved = employeesServiceImpl.save(DEPARTMENT_1_ID, employeeForSave);
		
		assertNotNull(employeeReturnSaved, "Employee could not be null");
		assertEquals(employeeForSave.getFirstName(), employeeReturnSaved.getFirstName(),
				String.format("First name of employee must be: %s", employeeForSave.getFirstName()));
		verify(departmentsRepository, times(1)).getOne(eq(DEPARTMENT_1_ID));
		verify(employeesRepository, times(1)).save(eq(employeeForSave));
	}

	@Test
	public void testSaveEmployee_NoEntityFoundException() throws Exception {
		// given
		Employee employeeForSave = new Employee();
		employeeForSave.setId(EMPLOYEE_1_ID);
		employeeForSave.setFirstName(EMPLOYEE_1_FIRST_NAME);
		employeeForSave.setAge(EMPLOYEE_1_AGE);
		
		// when
		when(departmentsRepository.getOne(anyLong())).thenReturn(null);

		// then
		Assertions.assertThrows(NoEntityFoundException.class, () -> {
			employeesServiceImpl.save(DEPARTMENT_1_ID, employeeForSave);
		});
		verify(departmentsRepository, times(1)).getOne(eq(DEPARTMENT_1_ID));
	}
	
	@Test
	public void testDeleteEmployee_employeeExists() throws Exception {
		// given
		Employee employeeForDelete = new Employee();
		employeeForDelete.setId(EMPLOYEE_1_ID);
		employeeForDelete.setFirstName(EMPLOYEE_1_FIRST_NAME);
		employeeForDelete.setAge(EMPLOYEE_1_AGE);
		
		// when
		when(employeesRepository.getOne(anyLong())).thenReturn(employeeForDelete);
		doNothing().when(employeesRepository).delete(ArgumentMatchers.<Employee>any());
		
		// then
		employeesServiceImpl.delete(EMPLOYEE_1_ID);
		verify(employeesRepository, times(1)).getOne(eq(EMPLOYEE_1_ID));
		verify(employeesRepository, times(1)).delete(eq(employeeForDelete));
	}

	@Test
	public void testDeleteEmployee_NoEntityFoundException() throws Exception {
		// when
		when(employeesRepository.getOne(anyLong())).thenReturn(null);

		// then
		Assertions.assertThrows(NoEntityFoundException.class, () -> {
			employeesServiceImpl.delete(EMPLOYEE_1_ID);
		});
		verify(employeesRepository, times(1)).getOne(eq(EMPLOYEE_1_ID));
	}
	
}
