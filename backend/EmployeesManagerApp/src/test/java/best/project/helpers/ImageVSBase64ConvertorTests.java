package best.project.helpers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class ImageVSBase64ConvertorTests {

	@Test
	void testImageToBase64Convertor() {
		String imageName = "employeeF1.jpg";
		String base64Image = ImageVSBase64Convertor.imageToBase64Convertor(imageName);
		assertNotNull(base64Image);
	}
}
