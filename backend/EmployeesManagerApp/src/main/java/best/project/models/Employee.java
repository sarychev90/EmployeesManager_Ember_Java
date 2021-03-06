package best.project.models;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "employees")
public class Employee implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "The database generated employee's ID")
	private Long id;

	@Column(name = "first_name")
	@ApiModelProperty(notes = "First name of the employee", required = true)
	private String firstName;

	@Column(name = "last_name")
	@ApiModelProperty(notes = "Last name of the employee", required = true)
	private String lastName;

	@Column(name = "patronymic_name")
	@ApiModelProperty(notes = "Patronymic name of the employee")
	private String patronymicName;

	@Column(name = "position")
	@ApiModelProperty(notes = "Possition number of the employee", required = true)
	private String position;

	@Column(name = "phone_number")
	@ApiModelProperty(notes = "Phone number of the employee", required = true)
	private String phoneNumber;

	@Column(name = "email_address")
	@ApiModelProperty(notes = "Email address of the employee", required = true)
	private String emailAddress;

	@Column(name = "age")
	@ApiModelProperty(notes = "Age of the employee")
	private int age;

	@Column(name = "salary")
	@ApiModelProperty(notes = "Salary of the employee", required = true)
	private BigDecimal salary;
	
	@Column(name = "photo", columnDefinition="text")
	@ApiModelProperty(notes = "Photo of the employee", required = false)
	private String photo;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
	@JsonIgnore
    private Department department;

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", patronymicName="
				+ patronymicName + ", position=" + position + ", phoneNumber=" + phoneNumber + ", emailAddress="
				+ emailAddress + ", age=" + age + ", salary=" + salary + ", photo=" + photo + ", department="
				+ department + "]";
	}
	
}
