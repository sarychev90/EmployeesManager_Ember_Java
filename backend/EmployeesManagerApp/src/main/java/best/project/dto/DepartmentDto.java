package best.project.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for department.
 */
@Getter
@Setter
public class DepartmentDto {
	
	private Long id;

	private String name;
	
    private List<EmployeeDto> employee;
}
