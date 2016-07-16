package service;

public class ServiceResponse {
	private boolean success = false;
	private String message = "";
	
	public ServiceResponse(boolean success, String message){
		this.success = success;
		this.message = message;
	}
	
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}		
}
