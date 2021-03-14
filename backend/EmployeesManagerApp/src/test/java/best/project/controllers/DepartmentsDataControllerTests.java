package best.project.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import best.project.dto.DepartmentDto;
import best.project.exeptions.NoEntityFoundException;
import best.project.models.Department;
import best.project.services.DepartmentsService;

@ExtendWith(MockitoExtension.class)
public class DepartmentsDataControllerTests {
	
	private static final Long DEPARTMENT_1_ID = 1l;
	private static final Long DEPARTMENT_2_ID = 2l;
	
	private static final String DEPARTMENT_1_NAME = "Financial department";
	private static final String DEPARTMENT_2_NAME = "Legal department";
	
	@Mock
    private DepartmentsService departmentsService;

	@InjectMocks
    private DepartmentsDataController departmentsDataController;

    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentsDataController).build();
    }

    @Test
    public void testGetDepartment_withResult() throws Exception {
        // given
		Department financeDepartment = new Department();
		financeDepartment.setName(DEPARTMENT_1_NAME);
		financeDepartment.setId(DEPARTMENT_1_ID);

        // when
        when(departmentsService.findById(anyLong())).thenReturn(financeDepartment);
       
        MvcResult result = mockMvc.perform(get(String.format("/api/department/%d", DEPARTMENT_1_ID))
        		.accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(DEPARTMENT_1_NAME))
                .andExpect(jsonPath("$.id").value(DEPARTMENT_1_ID))
                .andReturn();
		DepartmentDto departmentResponse = mapper.readValue(result.getResponse().getContentAsString(), DepartmentDto.class);

        //then
        verify(departmentsService, times(1)).findById(eq(DEPARTMENT_1_ID));
        assertNotNull(departmentResponse, "Department could not be null");
    }
    
    @Test
    public void testGetDepartment_NoEntityFoundException() throws Exception {
        // when
        when(departmentsService.findById(anyLong())).thenThrow(
        		new NoEntityFoundException(String.format("Could not find department by id: %d", DEPARTMENT_1_ID)));
        
        mockMvc.perform(get(String.format("/api/department/%d", DEPARTMENT_1_ID)))
                .andDo(print())
                .andExpect(status().isNotFound());
        //then
        verify(departmentsService, times(1)).findById(eq(DEPARTMENT_1_ID));
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testGetAllDepartments_withResult() throws Exception {
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
        when(departmentsService.findAll()).thenReturn(departments);
        
        MvcResult result = mockMvc.perform(get("/api/departments")
        		.accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
		List<Department> departmentsResponse = mapper.readValue(result.getResponse().getContentAsString(), List.class);

        //then
        verify(departmentsService, times(1)).findAll();
        assertNotNull(departmentsResponse, "List of departments could not be null");
        assertFalse(departmentsResponse.isEmpty(), "List of departments could not be empty");
        assertEquals(2, departmentsResponse.size(), "List of departments must conteins 2 departments");   
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testGetAllDepartments_NoEntityFoundException_EmptyResult() throws Exception {
        // when
        when(departmentsService.findAll()).thenThrow(
        		new NoEntityFoundException("Could not find departments in DB"));
        
        MvcResult result = mockMvc.perform(get("/api/departments"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
		List<Department> departmentsResponse = mapper.readValue(result.getResponse().getContentAsString(), List.class);

        //then
        verify(departmentsService, times(1)).findAll();
        assertNotNull(departmentsResponse, "List of departments could not be null");
        assertTrue(departmentsResponse.isEmpty(), "List of departments must be empty");
    }
    
    @Test
    public void testCreateDepartment_successfully() throws Exception {
        // given
    	Department financeDepartmentForSave = new Department();
    	financeDepartmentForSave.setName(DEPARTMENT_1_NAME);
    	
		Department financeDepartmentSaved = new Department();
		financeDepartmentSaved.setName(DEPARTMENT_1_NAME);
		financeDepartmentSaved.setId(DEPARTMENT_1_ID);
		
        // when
        when(departmentsService.save(ArgumentMatchers.<Department>any())).thenReturn(financeDepartmentSaved);
        
        mockMvc.perform(post("/api/department")
        		.accept(MediaType.APPLICATION_JSON)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(mapper.writeValueAsString(financeDepartmentForSave)))
                .andDo(print())
                .andExpect(status().isCreated());
        //then
        verify(departmentsService, times(1)).save(ArgumentMatchers.<Department>any());
    }
    
    @Test
    public void testUpdateDepartment_successfully() throws Exception {
        // given
    	Department financeDepartmentForSave = new Department();
    	financeDepartmentForSave.setName(DEPARTMENT_1_NAME);
    	financeDepartmentForSave.setId(DEPARTMENT_1_ID);
    	
		Department financeDepartmentSaved = new Department();
		financeDepartmentSaved.setName(DEPARTMENT_1_NAME);
		financeDepartmentSaved.setId(DEPARTMENT_1_ID);
		
        // when
        when(departmentsService.update(ArgumentMatchers.<Department>any())).thenReturn(financeDepartmentSaved);
        
        mockMvc.perform(put("/api/department")
        		.accept(MediaType.APPLICATION_JSON)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(mapper.writeValueAsString(financeDepartmentForSave)))
                .andDo(print())
                .andExpect(status().isOk());
        //then
        verify(departmentsService, times(1)).update(ArgumentMatchers.<Department>any());
    }
    
    @Test
	public void testUpdateDepartment_NoEntityFoundException() throws Exception {
    	 // given
    	Department financeDepartmentForSave = new Department();
    	financeDepartmentForSave.setName(DEPARTMENT_1_NAME);
    	financeDepartmentForSave.setId(DEPARTMENT_1_ID);
    	
    	// when
    	when(departmentsService.update(ArgumentMatchers.<Department>any())).thenThrow(
        		new NoEntityFoundException(String.format("Could not find department by id: %d", DEPARTMENT_1_ID)));
    	
		mockMvc.perform(put("/api/department")
		.accept(MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON)
		.content(mapper.writeValueAsString(financeDepartmentForSave)))
        .andDo(print())
		.andExpect(status().isNotModified());
		// then
		verify(departmentsService, times(1)).update(ArgumentMatchers.<Department>any());
	}
    
    @Test
    public void testDeleteDepartment_successfully() throws Exception {
        // when
		doNothing().when(departmentsService).delete(anyLong());
        mockMvc.perform(delete(String.format("/api/department/%d", DEPARTMENT_1_ID)))
                .andDo(print())
                .andExpect(status().isOk());
        //then
        verify(departmentsService, times(1)).delete(DEPARTMENT_1_ID);
    }
    
    @Test
	public void testDeleteDepartment_NoEntityFoundException() throws Exception {
		// when
    	doThrow(new NoEntityFoundException(String.
    			 format("Could not find department by id: %d", DEPARTMENT_1_ID))).
    	
    	when(departmentsService).delete(anyLong());
    			
		mockMvc.perform(delete(String.format("/api/department/%d", DEPARTMENT_1_ID))).
		andDo(print()).
		andExpect(status().isNotModified());
		// then
		verify(departmentsService, times(1)).delete(eq(DEPARTMENT_1_ID));
	}
}
