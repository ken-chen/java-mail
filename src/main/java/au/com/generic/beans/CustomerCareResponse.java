package au.com.generic.beans;

public class CustomerCareResponse {
private Boolean valid;
private String msg;
public Boolean getValid() {
	return valid;
}
public void setValid(Boolean valid) {
	this.valid = valid;
}
public String getMsg() {
	return msg;
}
public void setMsg(String msg) {
	this.msg = msg;
}
@Override
public String toString() {
	return "CustomerCareResponse [valid=" + valid + ", msg=" + msg + "]";
}

}
