package service;

public class TestService implements Service{

	private static final String NAME = "TEST";

	public static String getName() {
		return NAME;
	}

	@Override
	public Service create() {
		// TODO Auto-generated method stub
		return new TestService();
	}
	
}
