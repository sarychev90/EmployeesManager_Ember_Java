package best.project.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import best.project.dto.EmployeeDto;
import best.project.exeptions.NoEntityFoundException;
import best.project.models.Employee;
import best.project.services.EmployeesService;

@ExtendWith(MockitoExtension.class)
public class EmployeesDataControllerTests {
	
	private static final Long DEPARTMENT_1_ID = 1l;

	private static final Long EMPLOYEE_1_ID = 1l;
	private static final Long EMPLOYEE_2_ID = 2l;
	
	private static final String EMPLOYEE_1_FIRST_NAME = "Maxim";
	private static final String EMPLOYEE_2_FIRST_NAME = "Ivan";
	private static final int EMPLOYEE_1_AGE = 45;
	private static final int EMPLOYEE_2_AGE = 32;
	
	@Mock
    private EmployeesService employeesService;

	@InjectMocks
    private EmployeesDataController employeesDataController;

    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeesDataController).build();
    }

    @Test
    public void testGetEmployee_withResult() throws Exception {
        // given
		Employee employeeF1 = new Employee();
		employeeF1.setId(EMPLOYEE_1_ID);
		employeeF1.setFirstName(EMPLOYEE_1_FIRST_NAME);
		employeeF1.setAge(EMPLOYEE_1_AGE);

        // when
        when(employeesService.findById(anyLong())).thenReturn(employeeF1);
        
        MvcResult result = mockMvc.perform(get(String.format("/api/employee/%d", EMPLOYEE_1_ID))
        		.accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(EMPLOYEE_1_ID))
                .andExpect(jsonPath("$.firstName").value(EMPLOYEE_1_FIRST_NAME))
                .andExpect(jsonPath("$.age").value(EMPLOYEE_1_AGE))
                .andReturn();
		EmployeeDto employeeResponse = mapper.readValue(result.getResponse().getContentAsString(), EmployeeDto.class);

        //then
        verify(employeesService, times(1)).findById(eq(EMPLOYEE_1_ID));
        assertNotNull(employeeResponse, "Employee could not be null");
    }
    
    @Test
    public void testGetEmployee_NoEntityFoundException() throws Exception {
        // when
        when(employeesService.findById(anyLong())).thenThrow(
        		new NoEntityFoundException(String.format("Could not find employee by id: %d", EMPLOYEE_1_ID)));
        
        mockMvc.perform(get(String.format("/api/employee/%d", EMPLOYEE_1_ID)))
                .andDo(print())
                .andExpect(status().isNotFound());
        //then
        verify(employeesService, times(1)).findById(eq(EMPLOYEE_1_ID));
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testGetAllEmployees_withResult() throws Exception {
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
        when(employeesService.findAll()).thenReturn(employees);
        
        MvcResult result = mockMvc.perform(get("/api/employees")
        		.accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
		List<Employee> employeesResponse = mapper.readValue(result.getResponse().getContentAsString(), List.class);

        //then
        assertNotNull(employeesResponse, "List of employees could not be null");
        assertFalse(employeesResponse.isEmpty(), "List of employees could not be empty");
        assertEquals(2, employeesResponse.size(), "List of employees must conteins 2 employees");   
        verify(employeesService, times(1)).findAll();
    }
    
    @Test
    public void testGetAllEmployees_NoEntityFoundException() throws Exception {
        // when
        when(employeesService.findAll()).thenThrow(
        		new NoEntityFoundException("Could not find employees in DB"));
        
        mockMvc.perform(get("/api/employees"))
                .andDo(print())
                .andExpect(status().isNotFound());

        //then
        verify(employeesService, times(1)).findAll();
    }
    
    @Test
    public void testCreateEmployee_successfully() throws Exception {
        // given
		Employee financeEmployeeForSave = new Employee();
		financeEmployeeForSave.setFirstName(EMPLOYEE_1_FIRST_NAME);
		financeEmployeeForSave.setAge(EMPLOYEE_1_AGE);

		Employee financeEmployeeSaved = new Employee();
		financeEmployeeSaved.setId(EMPLOYEE_1_ID);
		financeEmployeeSaved.setFirstName(EMPLOYEE_1_FIRST_NAME);
		financeEmployeeSaved.setAge(EMPLOYEE_1_AGE);
		
        // when
        when(employeesService.save(anyLong(), ArgumentMatchers.<Employee>any())).thenReturn(financeEmployeeSaved);
        
        mockMvc.perform(post(String.format("/api/employee/%d", DEPARTMENT_1_ID))
        		.accept(MediaType.APPLICATION_JSON)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(mapper.writeValueAsString(financeEmployeeForSave)))
                .andDo(print())
                .andExpect(status().isCreated());
        //then
        verify(employeesService, times(1)).save(anyLong(), ArgumentMatchers.<Employee>any());
    }
    
    @Test
    public void testCreateEmployee_NoEntityFoundException() throws Exception {
        // given
		Employee financeEmployeeForSave = new Employee();
		financeEmployeeForSave.setFirstName(EMPLOYEE_1_FIRST_NAME);
		financeEmployeeForSave.setAge(EMPLOYEE_1_AGE);
		
        // when
        when(employeesService.save(anyLong(), ArgumentMatchers.<Employee>any())).
        thenThrow(new NoEntityFoundException(
        		String.format("Could not find department by employee id: %d", DEPARTMENT_1_ID)));
        
        mockMvc.perform(post(String.format("/api/employee/%d", DEPARTMENT_1_ID))
        		.accept(MediaType.APPLICATION_JSON)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(mapper.writeValueAsString(financeEmployeeForSave)))
                .andDo(print())
                .andExpect(status().isNotFound());
        //then
        verify(employeesService, times(1)).save(anyLong(), ArgumentMatchers.<Employee>any());
    }
    
    @Test
    public void testUpdateEmployee_successfully() throws Exception {
        // given
    	Employee financeEmployeeForSave = new Employee();
    	financeEmployeeForSave.setId(EMPLOYEE_1_ID);
    	financeEmployeeForSave.setFirstName(EMPLOYEE_1_FIRST_NAME);
    	financeEmployeeForSave.setAge(EMPLOYEE_1_AGE);

		Employee financeEmployeeSaved = new Employee();
		financeEmployeeSaved.setId(EMPLOYEE_2_ID);
		financeEmployeeSaved.setFirstName(EMPLOYEE_2_FIRST_NAME);
		financeEmployeeSaved.setAge(EMPLOYEE_2_AGE);
    	
        // when
        when(employeesService.update(ArgumentMatchers.<Employee>any())).thenReturn(financeEmployeeSaved);
        
        mockMvc.perform(put("/api/employee")
        		.accept(MediaType.APPLICATION_JSON)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(mapper.writeValueAsString(financeEmployeeForSave)))
                .andDo(print())
                .andExpect(status().isOk());
        //then
        verify(employeesService, times(1)).update(ArgumentMatchers.<Employee>any());
    }
    
    @Test
	public void testUpdateEmployee_NoEntityFoundException() throws Exception {
    	 // given
    	Employee financeEmployeeForSave = new Employee();
    	financeEmployeeForSave.setId(EMPLOYEE_1_ID);
    	financeEmployeeForSave.setFirstName(EMPLOYEE_1_FIRST_NAME);
    	financeEmployeeForSave.setAge(EMPLOYEE_1_AGE);
    	
    	// when
    	when(employeesService.update(ArgumentMatchers.<Employee>any())).
    	thenThrow(new NoEntityFoundException(
        		String.format("Could not find department by employee id: %d", EMPLOYEE_1_ID)));
    	
		mockMvc.perform(put("/api/employee")
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON)
			.content(mapper.writeValueAsString(financeEmployeeForSave)))
	        .andDo(print())
			.andExpect(status().isNotFound());
		// then
		verify(employeesService, times(1)).update(ArgumentMatchers.<Employee>any());
	}
    
    @Test
    public void testDeleteEmployee_successfully() throws Exception {
        // when
		doNothing().when(employeesService).delete(anyLong());
		
        mockMvc.perform(delete(String.format("/api/employee/%d", EMPLOYEE_1_ID)))
                .andDo(print())
                .andExpect(status().isOk());
        //then
        verify(employeesService, times(1)).delete(EMPLOYEE_1_ID);
    }
    
    @Test
	public void testDeleteEmployee_NoEntityFoundException() throws Exception {
		// when
    	doThrow(new NoEntityFoundException(String.
    			 format("Could not find employee by id: %d", EMPLOYEE_1_ID))).
    	when(employeesService).delete(anyLong());
    			
		mockMvc.perform(delete(String.format("/api/employee/%d", EMPLOYEE_1_ID))).
		andDo(print()).
		andExpect(status().isNotModified());
		
		// then
		verify(employeesService, times(1)).delete(eq(EMPLOYEE_1_ID));
	} 
}

