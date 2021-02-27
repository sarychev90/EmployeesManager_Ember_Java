package best.project.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "departments")
public class Department implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "The database generated department's ID")
	private int id;

	@Column(name = "name")
	@ApiModelProperty(notes = "Name of the department", required = true)
	private String name;
	
	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Employee> employee;

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", employee=" + employee + "]";
	}
	
}