package best.project.helpers;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import best.project.dao.DepartmentsRepository;
import best.project.models.Department;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TablePapulatorTests {

	private static final Logger LOGGER = Logger.getLogger(TablePapulatorTests.class.getName());

	@Autowired
	public DepartmentsRepository departmentsRepository;

	@Test
	void testLoadInitialDataToDB() {
		List<Department> departments = departmentsRepository.findAll();
		LOGGER.log(Level.INFO, "Found departments: " + departments.size());
		assertFalse(departments.isEmpty());
	}
}
