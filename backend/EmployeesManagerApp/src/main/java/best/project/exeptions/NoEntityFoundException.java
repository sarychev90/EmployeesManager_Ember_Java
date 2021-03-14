package best.project.exeptions;

public class NoEntityFoundException extends Exception {
	private static final long serialVersionUID = -796065227538587089L;
	public NoEntityFoundException(String message) {
        super(message);
    }
}
