package best.project.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import best.project.dto.EmployeeDto;
import best.project.models.Employee;

@Mapper
public interface EmployeeMapper {
	
	EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);
	
    EmployeeDto employeeToEmployeeDto(Employee employee);
}
