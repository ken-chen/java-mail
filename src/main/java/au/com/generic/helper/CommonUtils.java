package au.com.generic.helper;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import au.com.generic.beans.CustomerCareForm;
import au.com.generic.email.Attachment;
import au.com.generic.email.EmailManager;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

public class CommonUtils {
	
	private static Logger logger = Logger.getLogger(CommonUtils.class);
	private static final String FORMS_PHONE_EXP = "^([0-9\\.+-\\.)\\.(]|\\s){8,15}$";
	private static final String EMAIL_EXP = "^[A-Za-z0-9](([_\\.\\-\\+]?[a-zA-Z0-9]+)*)[_\\-]?@([A-Za-z0-9]+)(([\\.\\-]?[a-zA-Z0-9]+)*)\\.([A-Za-z]{2,})$";	
	/**
	 * @param job
	 * @param mailManager
	 * @param currentUserEmail
	 */
	public static void sendAdminEmail(EmailManager mailManager,CustomerCareForm customerCareForm) {
			
	    		String fromMailAddressForHolden = PropertyManager.getProperty("mail.from.holden");

	    		String subject = "Customer Care";
	  
	    		StringBuilder sb = new StringBuilder();
	    		sb.append("<html><body>");                                     
	    		sb.append("<b>Topic: Customer Care</b>");
	    		sb.append("<br><br>");
	    		
	    		sb.append("Name : " + customerCareForm.getFirstName() + " " + customerCareForm.getLastName());
	      		sb.append("<br><br>");
	      		
	      		
	    		sb.append("E-Mail Address : " + customerCareForm.getEmail());
	      		sb.append("<br><br>");
	      		
	    		sb.append("Phone No. : " + customerCareForm.getPhone());
	      		sb.append("<br><br>");
	      		
	    		sb.append("Preferred Contact Time : " + customerCareForm.getPreferredContactTime());
	      		sb.append("<br><br>");
	      		
	    		sb.append("Message body : " + customerCareForm.getComments());
	      		sb.append("<br><br>");
	      		
	    		sb.append("<br>");
	    		sb.append("</body></html>");
	    	
	    		//get from the properties
	    		String adminEmails = PropertyManager.getProperty("mail.admin");
	    		
	    		Set<String> alltoMails = new HashSet<String>();
	    		
	    		String[] temp = adminEmails.split(";");
		  		for(int cnt =0; cnt < temp.length ; cnt++){
		  			alltoMails.add(temp[cnt]);
		  		}
		  		
	    		for(String toMail: alltoMails){
	    			try {
	    				boolean success = mailManager.sendMail(toMail, fromMailAddressForHolden, fromMailAddressForHolden, subject, sb.toString(), "charset=UTF-8",new ArrayList<Attachment>());	
		    			System.out.println("email send success ? " + success);
		    			logger.info("email send success ? " + success);
					} catch (Exception e) {
						logger.error("email send falided  ",  e);	
						throw new RuntimeException("There is the error when sending the email, please try again later.");
					}
	    		  }	
		
	}
	
	
	public static void sendCustomerEmail(EmailManager mailManager,CustomerCareForm customerCareForm) {
		

		String fromMailAddressForUser = PropertyManager.getProperty("mail.from.user");
		String subject = "Customer Care";

		StringBuilder sb = new StringBuilder();
		sb.append("<html><body>");
 		
		sb.append("Hi!");
  		sb.append("<br><br>");
		sb.append("Thanks for submitting your feedback form to us. ");
  		sb.append("<br><br>");
		sb.append("We will review your request as soon as possible and get in touch. ");
  		sb.append("<br><br>");
		sb.append("Regards,");
  		sb.append("<br>");
  		sb.append("Customer Assistance team");
		sb.append("</body></html>");
	
		try {
			boolean success = mailManager.sendMail(customerCareForm.getEmail(), fromMailAddressForUser, fromMailAddressForUser, subject, sb.toString(), "charset=UTF-8",new ArrayList<Attachment>());	
			System.out.println("CustomerEmail send success ? " + success);
			logger.info("CustomerEmail send success ? " + success);
		} catch (Exception e) {
			logger.error("CustomerEmail send falided  ",  e);	
			throw new RuntimeException("There is the error when sending the CustomerEmail , please try again later.");
		}
		  
		
	}
	
	public static void validateForm(CustomerCareForm customerCareForm) {
		
		if(customerCareForm == null){
			throw new IllegalArgumentException("customerCareForm can not be empty");
		}
		
		if(StringUtils.isBlank(customerCareForm.getFirstName())){
			throw new IllegalArgumentException("FirstName field can not be empty");
		}
		
		if(StringUtils.isBlank(customerCareForm.getLastName())){
			throw new IllegalArgumentException("LastName field can not be empty");
		}
		
		if(!isValidEmailAddress(customerCareForm.getEmail())){
			throw new IllegalArgumentException("Email address field is not valid");	
		}
		
		if(!isValidFormsPhoneNumber(customerCareForm.getPhone())){
			throw new IllegalArgumentException("Phone field is not valid");
		}
		
		if(StringUtils.isBlank(customerCareForm.getPreferredContactTime())){
			throw new IllegalArgumentException("PreferredContactTime field can not be empty");
		}
		
		if(StringUtils.isBlank(customerCareForm.getComments())){
			throw new IllegalArgumentException("Comments field can not be empty");
		}
	}
	
	
	public static boolean isValidEmailAddress(String email) {

		if (StringUtils.isBlank(email))
			return true;

		Pattern pattern = Pattern.compile(EMAIL_EXP);

		Matcher matcher = pattern.matcher(email);

		return matcher.matches();
	}
	
	public static boolean isValidFormsPhoneNumber( String phone ) {
		if (StringUtils.isBlank(phone))
			return false;

		Pattern pattern = Pattern.compile(FORMS_PHONE_EXP);

		Matcher matcher = pattern.matcher(phone);

		return matcher.matches();
	}
	
	static XStream xstreamForJson = new XStream(new JsonHierarchicalStreamDriver() {

		public HierarchicalStreamWriter createWriter(Writer writer) {
			return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
		}
	});

	
	public static String getJsonString(Object object){
		return xstreamForJson.toXML(object);
	}	
}
