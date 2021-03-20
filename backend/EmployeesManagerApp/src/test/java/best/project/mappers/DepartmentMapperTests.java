package best.project.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import best.project.dto.DepartmentDto;
import best.project.models.Department;

public class DepartmentMapperTests {

	private static final Long DEPARTMENT_1_ID = 1l;
	private static final String DEPARTMENT_1_NAME = "Financial department";

	@Test
	public void testDepartmentToDepartmentDto() {

		// given
		Department financeDepartment = new Department();
		financeDepartment.setId(DEPARTMENT_1_ID);
		financeDepartment.setName(DEPARTMENT_1_NAME);

		// when
		DepartmentDto financeDepartmentDto = DepartmentMapper.INSTANCE.departmentToDepartmentDto(financeDepartment);

		//then
		assertThat(financeDepartmentDto.getId()).isEqualTo(DEPARTMENT_1_ID);
		assertThat(financeDepartmentDto.getName()).isEqualTo(DEPARTMENT_1_NAME);
	}

}
