package best.project.exeptions;

public class NoEntityFoundExeption extends Exception {
	private static final long serialVersionUID = -796065227538587089L;
	public NoEntityFoundExeption(String message) {
        super(message);
    }
}
