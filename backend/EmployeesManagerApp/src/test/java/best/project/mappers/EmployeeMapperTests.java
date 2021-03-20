package best.project.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import best.project.dto.EmployeeDto;
import best.project.models.Department;
import best.project.models.Employee;

public class EmployeeMapperTests {

	private static final Long DEPARTMENT_1_ID = 1l;
	private static final String DEPARTMENT_1_NAME = "Financial department";
	private static final Long EMPLOYEE_1_ID = 1l;
	private static final String EMPLOYEE_1_FIRST_NAME = "Maxim";
	private static final int EMPLOYEE_1_AGE = 45;

	@Test
	public void testEmployeeToEmployeeDto() {

		// given
		Employee employeeF1 = new Employee();
		employeeF1.setId(EMPLOYEE_1_ID);
		employeeF1.setFirstName(EMPLOYEE_1_FIRST_NAME);
		employeeF1.setAge(EMPLOYEE_1_AGE);
		Department financeDepartment = new Department();
		financeDepartment.setId(DEPARTMENT_1_ID);
		financeDepartment.setName(DEPARTMENT_1_NAME);
		employeeF1.setDepartment(financeDepartment);

		// when
		EmployeeDto financeEmployeeDto = EmployeeMapper.INSTANCE.employeeToEmployeeDto(employeeF1);

		//then
		assertThat(financeEmployeeDto.getId()).isEqualTo(EMPLOYEE_1_ID);
		assertThat(financeEmployeeDto.getFirstName()).isEqualTo(EMPLOYEE_1_FIRST_NAME);
		assertThat(financeEmployeeDto.getAge()).isEqualTo(EMPLOYEE_1_AGE);
	}

}
