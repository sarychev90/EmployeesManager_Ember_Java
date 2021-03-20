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

import best.project.exeptions.NoEntityFoundException;
import best.project.models.Department;
import best.project.repositories.DepartmentsRepository;
import best.project.services.impl.DepartmentsServiceImpl;

@ExtendWith(MockitoExtension.class)
public class DepartmentsServiceImplTest {

	private static final Long DEPARTMENT_1_ID = 1l;
	private static final Long DEPARTMENT_2_ID = 2l;

	private static final String DEPARTMENT_1_NAME = "Financial department";
	private static final String DEPARTMENT_2_NAME = "Legal department";

	@Mock
	private DepartmentsRepository departmentsRepository;

	@InjectMocks
	private DepartmentsServiceImpl departmentsServiceImpl;

	@Test
	public void testFindById_departmentExists() throws Exception {
		// given
		Department financeDepartment = new Department();
		financeDepartment.setName(DEPARTMENT_1_NAME);
		financeDepartment.setId(DEPARTMENT_1_ID);

		// when
		when(departmentsRepository.getOne(anyLong())).thenReturn(financeDepartment);

		// then
		Department foundDepartment = departmentsServiceImpl.findById(DEPARTMENT_1_ID);
		assertNotNull(foundDepartment, "Department could not be null");
		assertEquals(DEPARTMENT_1_NAME, foundDepartment.getName(),
				String.format("Name of department must be: %s", DEPARTMENT_1_NAME));
		verify(departmentsRepository, times(1)).getOne(eq(DEPARTMENT_1_ID));
	}

	@Test
	public void testFindById_NoEntityFoundException() throws Exception {
		// when
		when(departmentsRepository.getOne(anyLong())).thenReturn(null);

		// then
		Assertions.assertThrows(NoEntityFoundException.class, () -> {
			departmentsServiceImpl.findById(DEPARTMENT_1_ID);
		});
		verify(departmentsRepository, times(1)).getOne(eq(DEPARTMENT_1_ID));
	}

	@Test
	public void testFindAllDepartments_departmentsExist() throws Exception {
		// given
		List<Department> departments = new ArrayList<>();

		Department financeDepartment = new Department();
		financeDepartment.setName(DEPARTMENT_1_NAME);
		financeDepartment.setId(DEPARTMENT_1_ID);
		departments.add(financeDepartment);

		Department legalDepartment = new Department();
		financeDepartment.setName(DEPARTMENT_2_NAME);
		financeDepartment.setId(DEPARTMENT_2_ID);
		departments.add(legalDepartment);

		// when
		when(departmentsRepository.findAll()).thenReturn(departments);

		// then
		List<Department> departmentsFound = departmentsServiceImpl.findAll();
		assertNotNull(departmentsFound, "List of departments could not be null");
		assertFalse(departmentsFound.isEmpty(), "List of departments could not be empty");
		assertEquals(2, departmentsFound.size(), "List of departments must conteins 2 departments");
		verify(departmentsRepository, times(1)).findAll();
	}

	@Test
	public void testFindAllDepartments_NoEntityFoundException() throws Exception {
		// when
		when(departmentsRepository.findAll()).thenReturn(null);

		// then
		Assertions.assertThrows(NoEntityFoundException.class, () -> {
			departmentsServiceImpl.findAll();
		});
		verify(departmentsRepository, times(1)).findAll();
	}

	@Test
	public void testUpdateDepartment_departmentExists() throws Exception {
		// given
		Department financeDepartmentForUpdate = new Department();
		financeDepartmentForUpdate.setName(DEPARTMENT_1_NAME);
		financeDepartmentForUpdate.setId(DEPARTMENT_1_ID);

		Department financeDepartmentUpdated = new Department();
		financeDepartmentUpdated.setName(DEPARTMENT_1_NAME);
		financeDepartmentUpdated.setId(DEPARTMENT_1_ID);

		// when
		when(departmentsRepository.getOne(anyLong())).thenReturn(financeDepartmentForUpdate);
		when(departmentsRepository.save(ArgumentMatchers.<Department>any())).thenReturn(financeDepartmentUpdated);

		// then
		Department financeDepartmentReturnUpdated = departmentsServiceImpl.update(financeDepartmentForUpdate);
		assertNotNull(financeDepartmentReturnUpdated, "Department could not be null");
		assertEquals(financeDepartmentForUpdate.getName(), financeDepartmentReturnUpdated.getName(),
				String.format("Name of department must be: %s", financeDepartmentForUpdate.getName()));
		verify(departmentsRepository, times(1)).getOne(eq(DEPARTMENT_1_ID));
		verify(departmentsRepository, times(1)).save(eq(financeDepartmentForUpdate));
	}

	@Test
	public void testUpdateDepartment_NoEntityFoundException() throws Exception {
		// given
		Department financeDepartmentForUpdate = new Department();
		financeDepartmentForUpdate.setName(DEPARTMENT_1_NAME);
		financeDepartmentForUpdate.setId(DEPARTMENT_1_ID);

		// when
		when(departmentsRepository.getOne(anyLong())).thenReturn(null);

		// then
		Assertions.assertThrows(NoEntityFoundException.class, () -> {
			departmentsServiceImpl.update(financeDepartmentForUpdate);
		});
		verify(departmentsRepository, times(1)).getOne(eq(DEPARTMENT_1_ID));
	}

	@Test
	public void testSaveDepartment_successfully() throws Exception {
		// given
		Department financeDepartmentForSave = new Department();
		financeDepartmentForSave.setName(DEPARTMENT_1_NAME);

		Department financeDepartmentSaved = new Department();
		financeDepartmentSaved.setName(DEPARTMENT_1_NAME);
		financeDepartmentSaved.setId(DEPARTMENT_1_ID);

		// when
		when(departmentsRepository.save(ArgumentMatchers.<Department>any())).thenReturn(financeDepartmentSaved);

		// then
		Department financeDepartmentReturnSaved = departmentsServiceImpl.save(financeDepartmentForSave);
		assertNotNull(financeDepartmentReturnSaved, "Department could not be null");
		assertNotNull(financeDepartmentReturnSaved.getId(), "Departments id could not be null");
		assertEquals(financeDepartmentForSave.getName(), financeDepartmentReturnSaved.getName(),
				String.format("Name of department must be: %s", financeDepartmentForSave.getName()));
		verify(departmentsRepository, times(1)).save(eq(financeDepartmentForSave));
	}

	@Test
	public void testDeleteDepartment_departmentExists() throws Exception {
		// given
		Department financeDepartmentForDelete = new Department();
		financeDepartmentForDelete.setName(DEPARTMENT_1_NAME);
		financeDepartmentForDelete.setId(DEPARTMENT_1_ID);
		
		// when
		when(departmentsRepository.getOne(anyLong())).thenReturn(financeDepartmentForDelete);
		doNothing().when(departmentsRepository).delete(ArgumentMatchers.<Department>any());
		
		// then
		departmentsServiceImpl.delete(DEPARTMENT_1_ID);
		verify(departmentsRepository, times(1)).getOne(eq(DEPARTMENT_1_ID));
		verify(departmentsRepository, times(1)).delete(eq(financeDepartmentForDelete));
	}

	@Test
	public void testDeleteDepartment_NoEntityFoundException() throws Exception {
		// when
		when(departmentsRepository.getOne(anyLong())).thenReturn(null);

		// then
		Assertions.assertThrows(NoEntityFoundException.class, () -> {
			departmentsServiceImpl.delete(DEPARTMENT_1_ID);
		});
		verify(departmentsRepository, times(1)).getOne(eq(DEPARTMENT_1_ID));
	}

}
