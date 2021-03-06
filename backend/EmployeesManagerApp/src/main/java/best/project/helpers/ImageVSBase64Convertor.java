package best.project.helpers;

import java.io.InputStream;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageVSBase64Convertor {
	
	private static final Logger LOGGER = Logger.getLogger(ImageVSBase64Convertor.class.getName());
	
	public ImageVSBase64Convertor() {
	}
	
	public static String imageToBase64Convertor(String imageName) {
		byte[] fileContent = null;
		String encodedImage = null;
		try (InputStream imageInputeStream = ImageVSBase64Convertor.class.getResourceAsStream("/images/photos/"+imageName))
		{
			fileContent = imageInputeStream.readAllBytes();
			encodedImage = Base64.getEncoder().encodeToString(fileContent);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Problem with converting image to base64 "+ e);
		}
		LOGGER.log(Level.INFO, "Base64 photo: "+encodedImage.substring(0,  100));
		return "data:image/jpg;base64,"+encodedImage;
	}

}
