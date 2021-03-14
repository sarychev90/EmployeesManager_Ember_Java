package best.project.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for employee.
 */
@Getter	
@Setter
public class EmployeeDto {
	
	private Long id;

	private String firstName;

	private String lastName;

	private String patronymicName;

	private String position;

	private String phoneNumber;

	private String emailAddress;

	private int age;

	private BigDecimal salary;
	
	private String photo;
}
