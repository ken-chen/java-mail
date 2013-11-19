package au.com.generic.controller;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import au.com.generic.beans.CustomerCareForm;
import au.com.generic.beans.CustomerCareResponse;
import au.com.generic.email.EmailManager;
import au.com.generic.helper.CommonUtils;


 
@Controller
@RequestMapping("/customercare")
public class FormSubmitController {
	static Logger logger = Logger.getLogger(FormSubmitController.class);
	
		@Autowired
    	private EmailManager mailManager;
		
		public static final String SUCESSFUL_SEND = "The email has been send sucessfully." ;
		public static final String FAILED_SEND = "There is the error when send the email, please try again later." ;
    	
	    @RequestMapping(method=RequestMethod.GET)
	    public String showForm(ModelMap model){
	    	CustomerCareForm customerCareForm = new CustomerCareForm();
	        model.addAttribute("CUSTOMERCAREFORM", customerCareForm);
	        return "landing";
	    }

	    @RequestMapping(method=RequestMethod.POST)
	    public String processForm(@ModelAttribute(value="CUSTOMERCAREFORM") CustomerCareForm customerCareForm,BindingResult result,ModelMap model){
	    	CustomerCareResponse customerCareResponse = new CustomerCareResponse();
	    	if(result.hasErrors()){
	    		customerCareResponse.setValid(false);
	    		customerCareResponse.setMsg(FAILED_SEND);
	    		
	        }else{	        	
	            try {
	            	CommonUtils.validateForm(customerCareForm);
	            	
		            CommonUtils.sendAdminEmail(mailManager, customerCareForm);
		            
		            CommonUtils.sendCustomerEmail(mailManager, customerCareForm);
		            
		    		customerCareResponse.setValid(true);		
		    		customerCareResponse.setMsg(SUCESSFUL_SEND);
				} catch (Exception e) {
					customerCareResponse.setValid(false);
					customerCareResponse.setMsg(FAILED_SEND);
				}
	        }
	    	model.addAttribute("message", CommonUtils.getJsonString(customerCareResponse));
	        return "result";
	    }
}
