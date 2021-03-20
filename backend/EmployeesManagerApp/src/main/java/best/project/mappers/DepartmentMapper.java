package best.project.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import best.project.dto.DepartmentDto;
import best.project.models.Department;

@Mapper
public interface DepartmentMapper {
	
	DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);
	
    DepartmentDto departmentToDepartmentDto(Department department);
}
