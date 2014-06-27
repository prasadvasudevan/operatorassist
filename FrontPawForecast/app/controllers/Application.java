package controllers;





import static play.data.Form.form;

import java.awt.Menu;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;


import com.frontpaw.forecast.model.Chart;
import com.frontpaw.forecast.model.EventData;
import com.frontpaw.forecast.model.Measures;
import com.frontpaw.forecast.model.MenuChart;
import com.frontpaw.forecast.model.PerpetualEventData;
import com.frontpaw.forecast.schema.Measure;
import com.frontpaw.forecast.service.DataServiceI;
import com.frontpaw.forecast.service.DataServiceImpl;
import com.frontpaw.forecast.service.FPAuthorizationService;
import com.frontpaw.forecast.service.RestService;
import com.frontpaw.forecast.util.User;
import com.frontpaw.forecast.util.ComparisonDisplayData;
import com.frontpaw.forecast.service.ForecastRestService;
import com.frontpaw.forecast.util.Helper;
import com.frontpaw.forecast.util.MailSender;






import play.*;
import play.cache.Cache;
import play.data.Form;
import play.data.DynamicForm;
import play.libs.F.Function;
import play.data.validation.Constraints.Required;
import play.libs.F.Promise;
import play.libs.Json;
import play.libs.WS;
import play.libs.WS.WSRequestHolder;
import play.mvc.*;
import play.mvc.Http.Session;
import play.mvc.Http.Request;
import scala.util.parsing.json.JSON;

import views.html.*;
import views.html.helper.form;

// TODO: Auto-generated Javadoc
/**
 * The Class Application.
 */
public class Application extends Controller {
  
	 /**
     * Describes the hello form.
     */
    public static class Login {
        
        /** The username. */
        @Required public String username;
        
        /** The password. */
        @Required public String password;
        
        /** The message. */
        public String message;
    } 
    
    
 // -- Actions
    
    /**
  * Home page.
  * 
  *Renders the login form to the user
  *
  * @return the result
  */
    public static Result login() {
    	//return redirect("hello1");
    	//System.out.println(request().body());
    	Form<Login> loginForm = form(Login.class);
    	Login loginF = new Login();
    	loginF.message = "";
    	loginForm.fill(loginF);
    	//System.out.println("filed value="+loginForm.field("message").value());
       return ok(login.render(loginForm));
    //	return ok(aboutus.render());
       
    }
	
	public static Result checkLogin(){
			Form<Login> loginForm = form(Login.class);
			Login loginF = new Login();
			loginF.message = "";
			loginForm.fill(loginF);
			
			DynamicForm dynaform = form().bindFromRequest();
			
			String userName = dynaform.get("username");
	    	String password  = dynaform.get("password");
			RestService service = new RestService();
			try{
				User user = service.checkUser(userName,password);
				if(user!=null && user.getSearchMessage().equals("ValidUser")){
					
					session("user","Welcome " +user.getFirstName());
					session("email",user.getEmail());
					session("currenttime",new Long(System.currentTimeMillis()).toString());
					//System.out.println("description " + user.getDescription());
					//session("description",user.getDescription());
					return ok(whyitmatters.render());
					//redirect("http://localhost:9000/aboutus");
				}else {
					return ok(login.render(loginForm));
					//redirect("http://localhost:9000/login");

					//return ok(aboutus.render());					
				}
			}catch(Exception e){
				e.printStackTrace();
				return ok(login.render(loginForm));
				//redirect("http://localhost:9000/login");


			}
			//return ok(login.render(loginForm));
			//return ok(aboutus.render());
	
	}
	
	public static Result register(){
		
	
	        return ok(register.render());

	
	}
	
	
	public static Result viewsample(){
		
		
        return ok(viewsample.render());


	}

	
	public static Result operator(){
		
		
        return ok(operator.render());


}
	
	public static Result aboutus() {
		String mailMessage = "";
    	return ok(aboutus.render(mailMessage));
    }
	
	public static Result contactus(){
			Properties prop = null;
			Helper helper = new Helper();
			MailSender mailSender = new MailSender();
			//MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
			
			
			DynamicForm dynaform = form().bindFromRequest();
			String mailMessage = "";
			
			String userName = dynaform.get("name");
	    	String emailAddress  = dynaform.get("emailAddress");
			String message  = dynaform.get("message");

			try{
				prop = helper.getMailProperties();
				String subject = "Request from " + emailAddress ;
				String notification ="N";
				if((dynaform.get("updateFeatures")!=null && dynaform.get("updateFeatures").equals("1"))){
					subject =  subject + " - Please inform as new features are added";
					notification ="Y";
				
				}
				
				/*mail.setSubject(subject);
				mail.setRecipient(emailAddress);
				mail.setFrom("info@frontpaw.com");
				mail.send(message);*/

				//System.out.println("subject" + subject);
				mailSender.sendMail(prop,userName,"info@frontpaw.com",emailAddress,subject,message);
				mailMessage = "Mail sent successfully";

			}catch(Exception e){
				e.printStackTrace();
				return ok(aboutus.render(mailMessage));
				//redirect("http://localhost:9000/login");


			}
			//return ok(login.render(loginForm));
			return ok(aboutus.render(mailMessage));
	
	}

	public static Result createUser(){
			DynamicForm dynaform = form().bindFromRequest();
			String firstName="";
			String lastName ="";
			String email ="";
			String password ="";
			String company = "";
			String userId = "";
	    	firstName = dynaform.get("firstName");
	    	lastName = dynaform.get("lastName");
	    	email = dynaform.get("emailId");
	    	password = dynaform.get("password");
	    	company = dynaform.get("company");
	    	userId = dynaform.get("userId");
	    	
			RestService service = new RestService();
			String message = "";
			try{
				String response = service.RegisterUser(firstName,lastName,password,company,email,userId);
				if(response.equals("200")){
					//flash("message", "You have been registered succesfully");		
					session("user","Welcome " +firstName);
					return ok(aboutus.render(""));
					//return ok(register1.render());
				}else if(response.equals("400"))	{
					flash("message", "A user exists with this email already or check the mandatory fields");		
					return ok(register2.render());

				}else if(response.equals("500")){
					flash("message", "Internal server Error");			
					return ok(register3.render());

				}/*else {
					return ok(register3.render());
				}*/	


				//System.out.println("response " + response);
			}catch(Exception e){
				e.printStackTrace();
				return ok(register3.render());
			}
		//	Result ok = ok(message);
	        return ok(register.render());

	
	}

	
	public static Result updateuser(){

		DynamicForm dynaform = form().bindFromRequest();
		String firstName="";
		String password ="";
		String userId = "";
    	password = dynaform.get("userPassword");
    	
		RestService service = new RestService();
		String message = "";
		
		if(session("email")!=null){
			userId = session("email");
		}		
		try{
			String response = service.UpdateUser(userId, password);
			if(response.equals("200")){
				//flash("message", "You have been registered succesfully");		
				//session("user","Welcome " +firstName);
				flash("message", "your password has been updated successfully");		
				session().clear();

				return ok(updatemessage.render());
				//return ok(register1.render());
			}else if(response.equals("400"))	{
				flash("message", "A user exists with this email already or check the mandatory fields");		
				return ok(myaccount.render());

			}else if(response.equals("500")){
				flash("message", "Internal server Error");			
				return ok(myaccount.render());

			}/*else {
				return ok(register3.render());
			}*/	


			//System.out.println("response " + response);
		}catch(Exception e){
			e.printStackTrace();
			return ok(register3.render());
		}
	//	Result ok = ok(message);
        return ok(register.render());



	}
	
	
	
   /* public static Result comparisiondisplay() {
    	DataServiceI service = new DataServiceImpl();
		String []foreCastChartData = new String[7];
		Map <Integer, Object> foreCastchart = null;
   	    foreCastchart = service.setComparisionDisplay();
   	    LinkedHashMap<String,Object> allMap = (LinkedHashMap<String, Object>) foreCastchart.get(50);
 	    List<String> models =  (List<String>) allMap.get("firstSection");
 	    Map<String,Integer> datesLenghtMap = (Map<String, Integer>) foreCastchart.get(51);
 		// System.out.println(" dates length="+datesLenghtMap);
 	    Set<String> keyMeasures = (Set<String>)foreCastchart.get(53);
 	    Integer count = 0 ;
 	  	if(keyMeasures!=null)
 			count = keyMeasures.size()+2;
 		Integer perptulaCount = (Integer)foreCastchart.get(52);
 		for(Integer counter:datesLenghtMap.values()){
 			count = count+counter ;
 		}
 		count = count + perptulaCount;
 		count = count * 30 ;
 		count = count + 35 ;
 		// System.out.println("counter="+count);
 		Integer buleTab = count + 40 + 70;
     	//return ok(comparision.render(foreCastChartData,allMap,models,Json.stringify(Json.toJson(datesLenghtMap)),perptulaCount*30,count+70,buleTab.toString(),buleTab,Json.stringify(Json.toJson(keyMeasures))));
		List<String> modelList = new ArrayList<String>();
		List<String> firstSectionList = new ArrayList<String>();
		LinkedHashMap<String,List<List<String>>> perpertual = (LinkedHashMap)allMap.get("perpertual");
		LinkedHashMap<String,List<List<List<String>>>> periodMap = (LinkedHashMap)allMap.get("periodValues");
		LinkedHashMap<String,List<String>> firstSectionMap = (LinkedHashMap)allMap.get("firstSectionData");
		String width = new Integer((modelList.size()*200+143+5)).toString()+"px";
		String colspan = new Integer((modelList.size()+1)).toString(); 
		return ok(comparision.render(modelList,firstSectionList,perpertual,periodMap,firstSectionMap,colspan,width));
		
    }*/
	
	/*public static Result comparisiondisplay() {
		DataServiceI service = new DataServiceImpl();
		String []foreCastChartData = new String[7];
		Map <Integer, Object> foreCastchart = null;
		LinkedHashMap<String,List<String>> firstSectionData = new LinkedHashMap<String,List<String>>();
		LinkedHashMap<String,List<List<String>>> perpertualMap = new LinkedHashMap<String,List<List<String>>>();
		
	    foreCastchart = service.setComparisionDisplay();
	    LinkedHashMap<String,Object> allMap = (LinkedHashMap<String, Object>) foreCastchart.get(50);
	    List<String> models =  (List<String>) allMap.get("firstSection");
	    Map<String,Integer> datesLenghtMap = (Map<String, Integer>) foreCastchart.get(51);
		List<String> datesString = (List<String>)foreCastchart.get(54);
	    String[] firstSectiontitle = {"Net Income","Cum Net Income","Net Worth","Cash"};
		String width1 = new Integer((models.size()*200+143+5)).toString()+"px";
		for(String sectionTitle : firstSectiontitle){
			List<String> keyMeasuresList = new ArrayList<String>();
			for(String modelname : models){
				Map<String,String> keyMeasures = (Map<String,String>)allMap.get(modelname+"%%keyMeasures");

				if(keyMeasures !=null && keyMeasures.get(sectionTitle)!=null){
					String val = (String)keyMeasures.get(sectionTitle);

					keyMeasuresList.add(val);
					
				}	
			}
			firstSectionData.put(sectionTitle, keyMeasuresList);
		}
		
		for(String modelname :  models){
			List<List<String>> perpetualList = (List<List<String>>)allMap.get(modelname+"%%perpetual");
			perpertualMap.put(modelname,perpetualList);
		}
		
		LinkedHashMap<String,List<List<List<String>>>> periodMap = new LinkedHashMap<String,List<List<List<String>>>>();

		for(String date : datesString){
			boolean check = false;
			List<List<List<String>>> modelsDateList = getModelsDatePreFilledList(models.size());
			int a=0;
			List<String> nodatepos = new ArrayList<String>();
			for(String modelname :  models){
				Map<String,List<List<String>>> compareDatesMap = (Map<String,List<List<String>>>)allMap.get(modelname+"%%compareDates");
				if(compareDatesMap!=null){
					Map<String,List<List<String>>> dateMap =  (Map<String,List<List<String>>>)compareDatesMap.get(modelname);
					if(dateMap!=null && dateMap.containsKey(date)){
						check = true;
						List<List<String>> list = dateMap.get(date);
						modelsDateList.set(a,list);
					}
					a++;
				}
			}
			if(check){
				periodMap.put(date, modelsDateList);
			}	
			
		}
		
		return ok(comparision.render(models,firstSectionData,perpertualMap,periodMap,width1));
	
	}*/	
	
	
	public static Result comparisiondisplay() {
		try{
				Form<Login> loginForm = form(Login.class);
				Login loginF = new Login();
				loginF.message = "";
				loginForm.fill(loginF);

			if(session("user")!=null && session("user1")!=null){
				//Session session = Scope.Session.current();

				//session.remove("user1");
				//session().remove("user1");
			}
			if(session("currenttime")!=null){
				long currenttime = System.currentTimeMillis();
				long accesstime = new Long(session("currenttime")).longValue();
				long time = ((currenttime - accesstime)/(1000*60));
				System.out.println("testing time " + time);
				if(time>10){
					session().clear();
					return ok(login.render(loginForm));
				}else {
					session("currenttime", new Long(currenttime).toString());
				}
			}
		
	        NumberFormat formatter = new DecimalFormat("############");
	        formatter.setGroupingUsed(true);
	        formatter.setMaximumFractionDigits(0);
	        formatter.setMinimumFractionDigits(0);	
			
		DataServiceI service = new DataServiceImpl();
		
		String []foreCastChartData = new String[7];
		Map <Integer, Object> foreCastchart = null;
		LinkedHashMap<String,List<String>> firstSectionData = new LinkedHashMap<String,List<String>>();
		LinkedHashMap<String,List<List<String>>> perpertualMap = new LinkedHashMap<String,List<List<String>>>();
	    foreCastchart = service.setComparisionDisplay();
	    LinkedHashMap<String,Object> allMap = (LinkedHashMap<String, Object>) foreCastchart.get(50);
	    List<String> models =  (List<String>) allMap.get("firstSection");
	    Map<String,Integer> datesLenghtMap = (Map<String, Integer>) foreCastchart.get(51);
		List<String> datesString = (List<String>)foreCastchart.get(54);
	    String[] firstSectiontitle = {"Net Income","Cum Net Income","Net Worth","Cash"};
		String width1 = new Integer(((models.size()*200)+143+5)).toString()+"px";
		String noOfColumns = new Integer(models.size()).toString();
		List<String> firstColumn = new ArrayList<String>();
		firstColumn.add("Net Income");
		firstColumn.add("Cum Net Income");
		firstColumn.add("Net Worth");
		firstColumn.add("Cash");
		firstColumn.add("Perpetual");

		
		for(String sectionTitle : firstSectiontitle){
			List<String> keyMeasuresList = new ArrayList<String>();
			for(String modelname : models){
				Map<String,String> keyMeasures = (Map<String,String>)allMap.get(modelname+"%%keyMeasures");
				if(keyMeasures !=null && keyMeasures.get(sectionTitle)!=null){
					String val = (String)keyMeasures.get(sectionTitle);
					//System.out.println("keyMeasures val" + val);
					keyMeasuresList.add("$"+Math.round(formatter.parse(val).doubleValue()));
					
				}	
			}
			firstSectionData.put(sectionTitle, keyMeasuresList);
		}
		
		for(String modelname :  models){
			List<List<String>> perpetualList = (List<List<String>>)allMap.get(modelname+"%%perpetual");
			perpertualMap.put(modelname,perpetualList);
		}
		
		LinkedHashMap<String,List<List<List<String>>>> periodMap = new LinkedHashMap<String,List<List<List<String>>>>();
		for(String date : datesString){
			boolean check = false;
			List<List<List<String>>> modelsDateList = getModelsDatePreFilledList(models.size());
			int a=0;
			List<String> nodatepos = new ArrayList<String>();
			for(String modelname :  models){
				Map<String,List<List<String>>> compareDatesMap = (Map<String,List<List<String>>>)allMap.get(modelname+"%%compareDates");
				if(compareDatesMap!=null){
					Map<String,List<List<String>>> dateMap =  (Map<String,List<List<String>>>)compareDatesMap.get(modelname);
					if(dateMap!=null && dateMap.containsKey(date)){
						check = true;
						List<List<String>> list = dateMap.get(date);
						modelsDateList.set(a,list);
					}
					a++;
				}
			}
			if(check){
				periodMap.put(date, modelsDateList);
				firstColumn.add(date);

			}	
			
		}
		System.out.println("period map" + periodMap.size());
		String height = "";
		if(periodMap.size()==0)
			height = "height:350px";
		else if(periodMap.size()>0 && periodMap.size()<3)	
			height = "height:430px";
		else if(periodMap.size()>3 && periodMap.size()<6)	
			height = "height:500px";
		else 
			height = "height:600px";	
		/*for(String str : firstColumn){
			System.out.println(str);
		}*/
				System.out.println("period map" + height);

		
		return ok(comparision.render(models,firstSectionData,perpertualMap,periodMap,width1,noOfColumns,height));
		}catch(Exception e){
			e.printStackTrace();
			return ok(whyitmatters.render());

		}
	
	}
	
	public static Result comparisionSampleDisplay() {
		try{
				Form<Login> loginForm = form(Login.class);
				Login loginF = new Login();
				loginF.message = "";
				loginForm.fill(loginF);

			if(session("user")!=null && session("user1")!=null){
				//Session session = Scope.Session.current();

				//session.remove("user1");
				//session().remove("user1");
			}
			if(session("currenttime")!=null){
				long currenttime = System.currentTimeMillis();
				long accesstime = new Long(session("currenttime")).longValue();
				long time = ((currenttime - accesstime)/(1000*60));
				System.out.println("testing time " + time);
				if(time>10){
					session().clear();
					return ok(login.render(loginForm));
				}else {
					session("currenttime", new Long(currenttime).toString());
				}
			}
		
	        NumberFormat formatter = new DecimalFormat("############");
	        formatter.setGroupingUsed(true);
	        formatter.setMaximumFractionDigits(0);
	        formatter.setMinimumFractionDigits(0);	
			
		DataServiceI service = new DataServiceImpl();
		
		String []foreCastChartData = new String[7];
		Map <Integer, Object> foreCastchart = null;
		LinkedHashMap<String,List<String>> firstSectionData = new LinkedHashMap<String,List<String>>();
		LinkedHashMap<String,List<List<String>>> perpertualMap = new LinkedHashMap<String,List<List<String>>>();
	    foreCastchart = service.setComparisionDisplay();
	    LinkedHashMap<String,Object> allMap = (LinkedHashMap<String, Object>) foreCastchart.get(50);
	    List<String> models =  (List<String>) allMap.get("firstSection");
	    Map<String,Integer> datesLenghtMap = (Map<String, Integer>) foreCastchart.get(51);
		List<String> datesString = (List<String>)foreCastchart.get(54);
	    String[] firstSectiontitle = {"Net Income","Cum Net Income","Net Worth","Cash"};
		String width1 = new Integer(((models.size()*200)+143+5)).toString()+"px";
		String noOfColumns = new Integer(models.size()).toString();
		List<String> firstColumn = new ArrayList<String>();
		firstColumn.add("Net Income");
		firstColumn.add("Cum Net Income");
		firstColumn.add("Net Worth");
		firstColumn.add("Cash");
		firstColumn.add("Perpetual");

		
		for(String sectionTitle : firstSectiontitle){
			List<String> keyMeasuresList = new ArrayList<String>();
			for(String modelname : models){
				Map<String,String> keyMeasures = (Map<String,String>)allMap.get(modelname+"%%keyMeasures");
				if(keyMeasures !=null && keyMeasures.get(sectionTitle)!=null){
					String val = (String)keyMeasures.get(sectionTitle);
					//System.out.println("keyMeasures val" + val);
					keyMeasuresList.add("$"+Math.round(formatter.parse(val).doubleValue()));
					
				}	
			}
			firstSectionData.put(sectionTitle, keyMeasuresList);
		}
		
		for(String modelname :  models){
			List<List<String>> perpetualList = (List<List<String>>)allMap.get(modelname+"%%perpetual");
			perpertualMap.put(modelname,perpetualList);
		}
		
		LinkedHashMap<String,List<List<List<String>>>> periodMap = new LinkedHashMap<String,List<List<List<String>>>>();

		for(String date : datesString){
			boolean check = false;
			List<List<List<String>>> modelsDateList = getModelsDatePreFilledList(models.size());
			int a=0;
			List<String> nodatepos = new ArrayList<String>();
			for(String modelname :  models){
				Map<String,List<List<String>>> compareDatesMap = (Map<String,List<List<String>>>)allMap.get(modelname+"%%compareDates");
				if(compareDatesMap!=null){
					Map<String,List<List<String>>> dateMap =  (Map<String,List<List<String>>>)compareDatesMap.get(modelname);
					if(dateMap!=null && dateMap.containsKey(date)){
						check = true;
						List<List<String>> list = dateMap.get(date);
						modelsDateList.set(a,list);
					}
					a++;
				}
			}
			if(check){
				periodMap.put(date, modelsDateList);
				firstColumn.add(date);

			}	
			
		}
		System.out.println("period map" + periodMap.size());
		String height = "";
		if(periodMap.size()==0)
			height = "height:350px";
		else if(periodMap.size()>0 && periodMap.size()<3)	
			height = "height:430px";
		else if(periodMap.size()>3 && periodMap.size()<6)	
			height = "height:500px";
		else 
			height = "height:600px";	
		/*for(String str : firstColumn){
			System.out.println(str);
		}*/
				System.out.println("period map" + height);

		
		return ok(comparisionsample.render(models,firstSectionData,perpertualMap,periodMap,width1,noOfColumns,height));
		}catch(Exception e){
			e.printStackTrace();
			return ok(whyitmatters.render());

		}
	
	}
	
	
	private static  List<List<List<String>>> getModelsDatePreFilledList(int capacity){
		List<List<List<String>>> firstList = new ArrayList<List<List<String>>>();
		for(int a=0;a<(capacity-1);a++){
			List<List<String>> nildata = new ArrayList<List<String>>();
			List<String> nilList = new ArrayList<String>();
			nilList.add("NIL");
			nildata.add(nilList);
			firstList.add(nildata);
		}
	

		return firstList;
	}
	
	
	
	/*public static Result comparisiondisplay() {
				String email = session("email");
				System.out.println("email comparison display................." + email);
    			ForecastRestService FCservice = new ForecastRestService();
				String forecastResp=FCservice.getUserForecast(email);
				ComparisonDisplayData data = new ComparisonDisplayData();
				LinkedHashMap<String,Object> allMap = data.getComparisonData(forecastResp);
				List<String> modelList = new ArrayList<String>();
				List<String> firstSectionList = new ArrayList<String>();
				
				String[] models = (String[])allMap.get("modelskeys");
				String[] firstSection = (String[])allMap.get("firstSectionkeys");
				for(int a=0;a<models.length;a++)
					modelList.add(models[a]);
				for(int b=0;b<firstSection.length;b++)
					firstSectionList.add(firstSection[b]);
				LinkedHashMap<String,List<List<String>>> perpertual = (LinkedHashMap)allMap.get("perpertual");
				LinkedHashMap<String,List<List<List<String>>>> periodMap = (LinkedHashMap)allMap.get("periodValues");
				LinkedHashMap<String,List<String>> firstSectionMap = (LinkedHashMap)allMap.get("firstSectionData");
				String width = new Integer((modelList.size()*200+143+5)).toString()+"px";
				String colspan = new Integer((modelList.size()+1)).toString();
				
				
		
				
				return ok(comparision.render(modelList,firstSectionList,perpertual,periodMap,firstSectionMap,colspan,width));
		
    }*/
	
	
    public static Result getSampleBaselinesnapShot() {
    	String email = "";
		if(session("user")!=null && session("user1")!=null){
				//session.remove("user1");
			//session().remove("user1");
		}

		if(session("email")!=null){
			email = session("email");
			long currenttime = System.currentTimeMillis();
			long accesstime = new Long(session("currenttime")).longValue();
			long time = ((currenttime - accesstime)/(1000*60));
			System.out.println("testing time " + time);
			if(time>10){
				session().clear();
				//return ok(login.render(loginForm));
			}else {
				session("currenttime", new Long(currenttime).toString());
			}			
		}else {
			email = session("email1");
			long currenttime = System.currentTimeMillis();
			long accesstime = new Long(session("currenttime")).longValue();
			long time = ((currenttime - accesstime)/(1000*60));
			System.out.println("testing time " + time);
			if(time>10){
				session().clear();
				//return ok(login.render(loginForm));
			}else {
				session("currenttime", new Long(currenttime).toString());
			}			
		}
		try{

    	DataServiceI service = new DataServiceImpl();
    	//String map=service.setBaselineSnapShotData();
		LinkedHashMap baseLine = service.setBaselineSnapShotMapData(email);
		String map = (String)baseLine.get("baseLine");
		List<String> modelNameList= (ArrayList)baseLine.get("modelnameList");
    	//System.out.println("map size"+map);
    	return ok(baselinesample.render(map,modelNameList));
		}catch(Exception e){
			return ok(whyitmatters.render());
		}
		
    }
	
	
    public static Result getBaselinesnapShot() {
    	String email = "";
		if(session("user")!=null && session("user1")!=null){
				//session.remove("user1");
			//session().remove("user1");
		}

		if(session("email")!=null){
			email = session("email");
			long currenttime = System.currentTimeMillis();
			long accesstime = new Long(session("currenttime")).longValue();
			long time = ((currenttime - accesstime)/(1000*60));
			System.out.println("testing time " + time);
			if(time>10){
				session().clear();
				//return ok(login.render(loginForm));
			}else {
				session("currenttime", new Long(currenttime).toString());
			}			
		}else {
			email = session("email1");
			long currenttime = System.currentTimeMillis();
			long accesstime = new Long(session("currenttime")).longValue();
			long time = ((currenttime - accesstime)/(1000*60));
			System.out.println("testing time " + time);
			if(time>10){
				session().clear();
				//return ok(login.render(loginForm));
			}else {
				session("currenttime", new Long(currenttime).toString());
			}			
		}
		try{

    	DataServiceI service = new DataServiceImpl();
    	//String map=service.setBaselineSnapShotData();
		LinkedHashMap baseLine = service.setBaselineSnapShotMapData(email);
		String map = (String)baseLine.get("baseLine");
		List<String> modelNameList= (ArrayList)baseLine.get("modelnameList");
    	//System.out.println("map size"+map);
    	return ok(baseline.render(map,modelNameList));
		}catch(Exception e){
			return ok(whyitmatters.render());
		}
		
    }
    public static Result howitworks() {
    	//return redirect("hello1");
    	//System.out.println(request().body());
    	//Form<Login> loginForm = form(Login.class);
    	//Login loginF = new Login();
    	//loginF.message = "";
    	//loginForm.fill(loginF);
    	//System.out.println("filed value="+loginForm.field("message").value());
        //return ok(login.render(loginForm));
			long currenttime = System.currentTimeMillis();
			long accesstime = new Long(session("currenttime")).longValue();
			long time = ((currenttime - accesstime)/(1000*60));
			System.out.println("testing time " + time);
			if(time>10){
				session().clear();
				//return ok(login.render(loginForm));
			}else {
				session("currenttime", new Long(currenttime).toString());
			}		
    	return ok(howitworks.render());
       
    }
    public static Result whyitmatters() {
    	//return redirect("hello1");
    	//System.out.println(request().body());
    	//Form<Login> loginForm = form(Login.class);
    	//Login loginF = new Login();
    	//loginF.message = "";
    	//loginForm.fill(loginF);
    	//System.out.println("filed value="+loginForm.field("message").value());
        //return ok(login.render(loginForm));
			long currenttime = System.currentTimeMillis();
			long accesstime = new Long(session("currenttime")).longValue();
			long time = ((currenttime - accesstime)/(1000*60));
			System.out.println("testing time " + time);
			if(time>10){
				session().clear();
				//return ok(login.render(loginForm));
			}else {
				session("currenttime", new Long(currenttime).toString());
			}		
    	return ok(whyitmatters.render());
       
    }
	
	public static Result privacypolicy() {
			long currenttime = System.currentTimeMillis();
			long accesstime = new Long(session("currenttime")).longValue();
			long time = ((currenttime - accesstime)/(1000*60));
			System.out.println("testing time " + time);
			if(time>10){
				session().clear();
				//return ok(login.render(loginForm));
			}else {
				session("currenttime", new Long(currenttime).toString());
			}
    	return ok(privacypolicy.render());
       
    }
	
	public static Result legalstatement() {
			long currenttime = System.currentTimeMillis();
			long accesstime = new Long(session("currenttime")).longValue();
			long time = ((currenttime - accesstime)/(1000*60));
			System.out.println("testing time " + time);
			if(time>10){
				session().clear();
				//return ok(login.render(loginForm));
			}else {
				session("currenttime", new Long(currenttime).toString());
			}
    	return ok(legalstatement.render());
       
    }

	public static Result termsandservice() {
			long currenttime = System.currentTimeMillis();
			long accesstime = new Long(session("currenttime")).longValue();
			long time = ((currenttime - accesstime)/(1000*60));
			System.out.println("testing time " + time);
			if(time>10){
				session().clear();
				//return ok(login.render(loginForm));
			}else {
				session("currenttime", new Long(currenttime).toString());
			}
    	return ok(termsandservice.render());
       
    }

    
    
    public static Result myaccount() {
    	//return redirect("hello1");
    	//System.out.println(request().body());
    	//Form<Login> loginForm = form(Login.class);
    	//Login loginF = new Login();
    	//loginF.message = "";
    	//loginForm.fill(loginF);
    	//System.out.println("filed value="+loginForm.field("message").value());
        //return ok(login.render(loginForm));
    	return ok(myaccount.render());
       
    }
    
  
    
    /**
     * Gets the default report.
     *
     *Called on initial login with default report of Total Income and Total Expense
     *
     * @return the default report
     */
/*     public static Result getDefaultReport() {
    	
			
			    Form<Login> loginForm = form(Login.class);
				Login loginF = new Login();
				loginF.message = "";
				loginForm.fill(loginF);
			
			    if(session("user")!=null){	
					String email = session("email");
					//System.out.println("email data " + email);
					DataServiceI service = new DataServiceImpl();
					String [][]foreCastChartData = new String[5][5];
					Map<Integer, List<Object>> foreCastcharts = service.getReport(new HashMap<String, Integer>(),email);
        	   
					List<Object> displayChartData = foreCastcharts.get(0);
					MenuChart menuchart=(MenuChart)displayChartData.get(0);
					List<Measures> measures = menuchart.getMenuMeasures();
					Map<String, List<String>> accountMap = service.getAccountMap(measures);
					String accountMapdata = Json.stringify(Json.toJson(accountMap));
					int plots=0;
        	   
					for(List<Object> foreCastchart:foreCastcharts.values()){       		   
        	   
						MenuChart chartmenu=(MenuChart)foreCastchart.get(0);
						foreCastChartData[plots][0]=Json.stringify(Json.toJson(chartmenu.getCharts()));        		   
						foreCastChartData[plots][1] = Json.stringify(Json.toJson(foreCastchart.get(1))); 
						//System.out.println(foreCastChartData[plots][1]);
						foreCastChartData[plots][2] = Json.stringify(Json.toJson(foreCastchart.get(2)));
						//System.out.println("accountMapdata: "+accountMapdata);
        		   //System.out.println("foreCastChartData: "+foreCastChartData);
						//System.out.println("foreCastChartData0: "+plots+foreCastChartData[plots][0]);
						//System.out.println("foreCastChartData1: "+plots+foreCastChartData[plots][1]);
						//System.out.println("foreCastChartData2: "+plots+foreCastChartData[plots][2]);
						++plots;  		   
            	   

        	   }
        	   
        	    
        	   
        	   return ok(report.render("FrontPaw",foreCastChartData,0,accountMapdata));
				   
			}else {
					//System.out.println("else of get defaultReport");
					return ok(login.render(loginForm));

			}
			
    } */
	
    
    
    public static Result getDefaultTemplateReport() {
		Form<Login> loginForm = form(Login.class);
		Login loginF = new Login();
		loginF.message = "";
		loginForm.fill(loginF);
		try{
		String user = request().getQueryString("template");
	    if((session("user1")==null)){	
	    	session("user1","Welcome " +user);
			session("userval",user);
	    	session("email1",user);
			long currenttime = System.currentTimeMillis();
			if(session("currenttime")!=null){
				long accesstime = new Long(session("currenttime")).longValue();
				long time = ((currenttime - accesstime)/(1000*60));
				System.out.println("testing  time " + time);
				if(time>10){
					session().clear();
					return ok(login.render(loginForm));
				}else {
					session("currenttime", new Long(currenttime).toString());
				}
			}else {
				session("currenttime", new Long(currenttime).toString());
			}
	    }else {
	    	//session("temp",user);
			user = session("userval");

	    }

		System.out.println("user value in defaulttemplate " + user);
		ForecastRestService rest = new ForecastRestService();
		String forecastid = rest.getForeCastId(user);
		if(forecastid.length()>0){
		Hashtable authorization = new FPAuthorizationService().getAuthorization();
		String URL = "http://pmxrestenv-dev.elasticbeanstalk.com/api/forecasts/"+forecastid;
		WSRequestHolder holder =  WS.url(URL);
		holder.setHeader("Authorization", (String)authorization.get("Authorization"));
		holder.setHeader("date", (String)authorization.get("date"));
		holder.setHeader("x-fpws-authorization", (String)authorization.get("x-fpws-authorization"));
		return async(holder.get().map(new Function<WS.Response, Result>(){
				
	        public Result apply(WS.Response response) throws Exception {
	        ArrayList<String> forecastnameList = new ArrayList<String>();	
	        	
       		StringWriter writer = new StringWriter();
	        IOUtils.copy(response.getBodyAsStream(), writer, "UTF-8");
	        String body = writer.toString();
	       // System.out.println("response body " + body);
			/*File file = new File("d:/frontpawforecast.xml");
			 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(body);
			bw.close();*/
	        
	        String email = "";
	        DataServiceI service = new DataServiceImpl();
	        String [][]foreCastChartData = new String[5][5];
			Map<Integer, List<Object>> foreCastcharts = service.getReport(new HashMap<String, Integer>(),email,response.getBodyAsStream(),forecastnameList);
			if(forecastnameList!=null && forecastnameList.size()>0){
				session("forecastname",forecastnameList.get(0));
			}	
 
			if(foreCastcharts!=null && foreCastcharts.size()>0){
			List<Object> displayChartData = foreCastcharts.get(0);
			List<Object> dateobj = foreCastcharts.get(150);
			
			Integer datesize = (Integer)dateobj.get(0);
			String dateval = ""+datesize;
				 //  Integer datesize = 	(Integer)foreCastcharts.get(1);
	        	   MenuChart menuchart=(MenuChart)displayChartData.get(0);
	    		   List<Measures> measures = menuchart.getMenuMeasures();
	        	   Map<String, List<String>> accountMap = service.getAccountMap(measures);
	        	   String accountMapdata = Json.stringify(Json.toJson(accountMap));
	        	   int plots=0;
	        	   
	        	   for(List<Object> foreCastchart:foreCastcharts.values()){       		   
	        		   if(!(foreCastchart.get(0) instanceof Integer)){
	        		   MenuChart chartmenu=(MenuChart)foreCastchart.get(0);
	           		   foreCastChartData[plots][0]=Json.stringify(Json.toJson(chartmenu.getCharts()));

					   if(foreCastchart.get(1)!=null){
						   foreCastChartData[plots][1] = Json.stringify(Json.toJson((List<EventData>)foreCastchart.get(1)));
					   }	
					   if(foreCastchart.get(2)!=null){
						//foreCastChartData[plots][1] = Json.stringify(Json.toJson(foreCastchart.get(1)));        		   
						foreCastChartData[plots][2] = Json.stringify(Json.toJson(foreCastchart.get(2)));
					   }	
	        		   ++plots;  
	        		   }

	        	   }
	        	
	        	
	        	
	        	//;
	        	
	            //return ok("Feed title:" + response.asJson().findPath("title"));
	        	//   System.out.println("date val " + dateval);
	        	   return ok(viewsamplereport.render("FrontPaw",foreCastChartData,0,accountMapdata,dateval,"Ratio,Net Worth","Ratio,Net Income"));
				}else {
				   return ok(message.render());
				}
	        }

		}));
		
		}else {
			return ok(message.render());

		}
	    
		
		}catch(Exception e){
			e.printStackTrace();
			return ok(login.render(loginForm));

		}
	//	Promise<WS.Response> homePage = WS.url("http://mysite.com").;

	
	    /*if(session("user")!=null){	


	   
	    
	   
		   
	}*/
	
}	
    
    public static Result getDefaultReport() {
	    		Form<Login> loginForm = form(Login.class);
	    		Login loginF = new Login();
	    		loginF.message = "";
	    		loginForm.fill(loginF);
	    		try{
	    		String user ="";
				if(session("user")!=null && session("user1")!=null){
					//session.remove("user1");
					session().remove("user1");
				}
			    if(session("user")!=null){	
					long currenttime = System.currentTimeMillis();
					long accesstime = new Long(session("currenttime")).longValue();
					long time = ((currenttime - accesstime)/(1000*60));
					System.out.println("testing time " + time);
					if(time>10){
						session().clear();
						return ok(login.render(loginForm));
					}else {
						session("currenttime", new Long(currenttime).toString());
					}
			    	if(session("email")!=null)
			    		 user = session("email");
			    	else 
			    		user = session("email1");
	    		
	    		
	    		ForecastRestService rest = new ForecastRestService();
	    		String forecastid = rest.getForeCastId(user);
	    		if(forecastid.length()>0){
				Hashtable authorization = new FPAuthorizationService().getAuthorization();
				String URL = "http://pmxrestenv-dev.elasticbeanstalk.com/api/forecasts/"+forecastid;
				WSRequestHolder holder =  WS.url(URL);
				holder.setHeader("Authorization", (String)authorization.get("Authorization"));
				holder.setHeader("date", (String)authorization.get("date"));
				holder.setHeader("x-fpws-authorization", (String)authorization.get("x-fpws-authorization"));
				return async(holder.get().map(new Function<WS.Response, Result>(){
	    	        public Result apply(WS.Response response) throws Exception {
	    	        ArrayList<String> forecastnameList = new ArrayList<String>();	
   	        		StringWriter writer = new StringWriter();
	    	        IOUtils.copy(response.getBodyAsStream(), writer, "UTF-8");
	    	        String body = writer.toString();
	    	       // System.out.println("response body " + body);
				/*	File file = new File("d:/frontpawforecast2.xml");
					// if file doesnt exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}
					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(body);
					bw.close();	*/	        
	    	        String email = "";
	    	        DataServiceI service = new DataServiceImpl();
	    	        String [][]foreCastChartData = new String[5][5];
	    			Map<Integer, List<Object>> foreCastcharts = service.getReport(new HashMap<String, Integer>(),email,response.getBodyAsStream(),forecastnameList);
	    			if(forecastnameList!=null && forecastnameList.size()>0){
	    				session("forecastname",forecastnameList.get(0));
	    			}	
  	   
					if(foreCastcharts!=null && foreCastcharts.size()>0){
	    			List<Object> displayChartData = foreCastcharts.get(0);
	    			List<Object> dateobj = foreCastcharts.get(150);
	    			
	    			Integer datesize = (Integer)dateobj.get(0);
	    			String dateval = ""+(datesize);
	    				 //  Integer datesize = 	(Integer)foreCastcharts.get(1);
	    	        	   MenuChart menuchart=(MenuChart)displayChartData.get(0);
	    	    		   List<Measures> measures = menuchart.getMenuMeasures();
	    	        	   Map<String, List<String>> accountMap = service.getAccountMap(measures);
	    	        	   String accountMapdata = Json.stringify(Json.toJson(accountMap));
	    	        	   int plots=0;
	    	        	   
	    	        	   for(List<Object> foreCastchart:foreCastcharts.values()){       		   
	    	        		   if(!(foreCastchart.get(0) instanceof Integer)){
	    	        		   MenuChart chartmenu=(MenuChart)foreCastchart.get(0);
	    	           		   foreCastChartData[plots][0]=Json.stringify(Json.toJson(chartmenu.getCharts()));
	    	           		  // System.out.println("report values" + plots + "  "+ foreCastChartData[plots][0]);
	    					   if(foreCastchart.get(1)!=null){
	    						   foreCastChartData[plots][1] = Json.stringify(Json.toJson((List<EventData>)foreCastchart.get(1)));
	    					   }	
	    					   if(foreCastchart.get(2)!=null){
	    						//foreCastChartData[plots][1] = Json.stringify(Json.toJson(foreCastchart.get(1)));        		   
	    						foreCastChartData[plots][2] = Json.stringify(Json.toJson(foreCastchart.get(2)));
	    					   }	
	    	        		   ++plots;  
	    	        		   }

	    	        	   }
	    	        	
	    	        	
	    	        	
	    	        	//;
	    	        	
	    	            //return ok("Feed title:" + response.asJson().findPath("title"));
	    	        	 //  System.out.println("date val " + dateval);
	    	        	   return ok(report.render("FrontPaw",foreCastChartData,0,accountMapdata,dateval,"Ratio,Net Worth","Ratio,Net Income"));
						}else {
						   return ok(message.render());

						}
						
	    	        }

	    		}));
				
				}else {
					return ok(message.render());

				}
			    }else {
				//	System.out.println("else of get defaultReport");
					return ok(login.render(loginForm));

			    }
	    		
	    		}catch(Exception e){
	    			e.printStackTrace();
					return ok(login.render(loginForm));

	    		}
    		//	Promise<WS.Response> homePage = WS.url("http://mysite.com").;

			
			    /*if(session("user")!=null){	


        	   
        	    
        	   
				   
			}*/
			
    }	
    
    
	
    public static Result getOpsDefaultReport() {
		Form<Login> loginForm = form(Login.class);
		Login loginF = new Login();
		loginF.message = "";
		loginForm.fill(loginF);
		try{
	    //if(session("user")!=null){	

		//String user = session("email");
		String userId = "";	
		DynamicForm dynaform = form().bindFromRequest();
		if(dynaform.get("emailId")!=null){
			userId = dynaform.get("emailId");
		}else {
			userId = request().getQueryString("template");
			if(userId==null)
				userId = request().getQueryString("userId");

			
		}

		session().clear();
		session("user","operator");
		session("email",userId);
		session("currenttime",new Long(System.currentTimeMillis()).toString());
		System.out.println("********userId********"+userId);
	    ForecastRestService rest = new ForecastRestService();
		String forecastid = rest.getForeCastId(userId);
		
		Hashtable authorization = new FPAuthorizationService().getAuthorization();
		String URL = "http://pmxrestenv-dev.elasticbeanstalk.com/api/forecasts/"+forecastid;
		WSRequestHolder holder =  WS.url(URL);
		holder.setHeader("Authorization", (String)authorization.get("Authorization"));
		holder.setHeader("date", (String)authorization.get("date"));
		holder.setHeader("x-fpws-authorization", (String)authorization.get("x-fpws-authorization"));
		return async(holder.get().map(new Function<WS.Response, Result>(){
	        public Result apply(WS.Response response) throws Exception {
    	    ArrayList<String> forecastnameList = new ArrayList<String>();	
       		StringWriter writer = new StringWriter();
	        IOUtils.copy(response.getBodyAsStream(), writer, "UTF-8");
	        String body = writer.toString(); 
	        String email = "";
	        DataServiceI service = new DataServiceImpl();
	        String [][]foreCastChartData = new String[5][5];
			Map<Integer, List<Object>> foreCastcharts = service.getReport(new HashMap<String, Integer>(),email,response.getBodyAsStream(),forecastnameList);
			if(forecastnameList!=null && forecastnameList.size()>0){
				session("forecastname",forecastnameList.get(0));
			}	
 
	   
	    
			List<Object> displayChartData = foreCastcharts.get(0);
			List<Object> dateobj = foreCastcharts.get(150);
			String dateval = "";
			if(dateobj!=null && dateobj.size()>0){
				Integer datesize = (Integer)dateobj.get(0);
				dateval = ""+datesize;
			}
				 //  Integer datesize = 	(Integer)foreCastcharts.get(1);
				  
	        	   MenuChart menuchart=(MenuChart)displayChartData.get(0);
	    		   List<Measures> measures = menuchart.getMenuMeasures();
	        	   Map<String, List<String>> accountMap = service.getAccountMap(measures);
	        	   String accountMapdata = Json.stringify(Json.toJson(accountMap));
	        	   int plots=0;
	        	   
	        	   for(List<Object> foreCastchart:foreCastcharts.values()){       		   
	        		   if(!(foreCastchart.get(0) instanceof Integer)){
	        		   MenuChart chartmenu=(MenuChart)foreCastchart.get(0);
	           		   foreCastChartData[plots][0]=Json.stringify(Json.toJson(chartmenu.getCharts()));

					   if(foreCastchart.get(1)!=null){
						   foreCastChartData[plots][1] = Json.stringify(Json.toJson((List<EventData>)foreCastchart.get(1)));
					   }	
					   if(foreCastchart.get(2)!=null){
						//foreCastChartData[plots][1] = Json.stringify(Json.toJson(foreCastchart.get(1)));        		   
						foreCastChartData[plots][2] = Json.stringify(Json.toJson(foreCastchart.get(2)));
					   }	
	        		   ++plots;  
	        		   }

	        	   }
	        	
	        	
	        	
	        	//;
	        	
	            //return ok("Feed title:" + response.asJson().findPath("title"));
	        	   //System.out.println("date val " + dateval);
	        	   return ok(report.render("FrontPaw",foreCastChartData,0,accountMapdata,dateval,"Ratio,Net Worth","Ratio,Net Income"));

	        }

		}));
	    /*}else {
			System.out.println("else of get defaultReport");
			return ok(login.render(loginForm));

	    }*/
		
		}catch(Exception e){
			e.printStackTrace();
			return ok(login.render(loginForm));

		}
	//	Promise<WS.Response> homePage = WS.url("http://mysite.com").;

	
	    /*if(session("user")!=null){	


	   
	    
	   
		   
	}*/
	
}
    
    /**
     * Reload report.
     *
     *Called when any date selector or forecast parameter is selected
     *
     * @param queryString the query string
     * @return the result
     * 
     */
    public static Result reloadReport() {
		Form<Login> loginForm = form(Login.class);
		Login loginF = new Login();
		loginF.message = "";
		loginForm.fill(loginF);

    	/* Map<String, Integer> params =  new HashMap<String, Integer>();
    	String forecastParam="";
    	String forecastParam1="";
    	int dateCount=0;
		queryString = request().getQueryString("dataCount");
		dateCount = Integer.parseInt(queryString); 	
		forecastParam = request().getQueryString("forecastParams");
		forecastParam1 = request().getQueryString("forecastParams1");
		forecastParam=forecastParam.concat(","+forecastParam1);
		params.put(forecastParam, dateCount);*/
	
	
		try{
			String user = "";
		    if(session("user")!=null || session("user1")!=null){	
					long currenttime = System.currentTimeMillis();
					long accesstime = new Long(session("currenttime")).longValue();
					long time = ((currenttime - accesstime)/(1000*60));
					System.out.println("testing time " + time);
					if(time>10){
						session().clear();
						return ok(login.render(loginForm));
					}else {
						session("currenttime", new Long(currenttime).toString());
					}

			
		    	if(session("email")!=null)
		    		 user = session("email");
		    	else 
		    		 user = session("email1");
		ForecastRestService rest = new ForecastRestService();
		String forecastid = rest.getForeCastId(user);
		Hashtable authorization = new FPAuthorizationService().getAuthorization();
		String URL = "http://pmxrestenv-dev.elasticbeanstalk.com/api/forecasts/"+forecastid;
		WSRequestHolder holder =  WS.url(URL);
		holder.setHeader("Authorization", (String)authorization.get("Authorization"));
		holder.setHeader("date", (String)authorization.get("date"));
		holder.setHeader("x-fpws-authorization", (String)authorization.get("x-fpws-authorization"));
		return async(holder.get().map(new Function<WS.Response, Result>(){
	        public Result apply(WS.Response response) throws Exception {
    	        ArrayList<String> forecastnameList = new ArrayList<String>();	

	        	int dateCount=0;
	        	String forecastParam="";
	        	String forecastParam1="";

	        	dateCount = Integer.parseInt(request().getQueryString("dataCount"));
	    		forecastParam = request().getQueryString("forecastParams");
	    		forecastParam1 = request().getQueryString("forecastParams1");
	    		forecastParam=forecastParam.concat(","+forecastParam1);
	    		Map<String, Integer> params =  new HashMap<String, Integer>();
	    		params.put(forecastParam, dateCount);
/*	    		StringWriter writer = new StringWriter();
	    		IOUtils.copy(response.getBodyAsStream(), writer, "UTF-8");
	    		String body = writer.toString(); 
*/	    		String email = "";
	    		DataServiceI service = new DataServiceImpl();
	    		String [][]foreCastChartData = new String[5][5];
	    		Map<Integer, List<Object>> foreCastcharts = service.getReloadReport(params,email,response.getBodyAsStream(),forecastnameList);
					if(forecastnameList!=null && forecastnameList.size()>0){
						session("forecastname",forecastnameList.get(0));
					}	

					String dateval = "";
	    		   List<Object> displayChartData = foreCastcharts.get(0);
	    		   List<Object> dateobj = foreCastcharts.get(150);
				   if(dateobj!=null && dateobj.size()>0 ){
	    			 Integer datesize = (Integer)dateobj.get(0);
	    			  dateval = ""+datesize;
				   } 	
					
				   String 	accountMapdata = "";
				   if(displayChartData!=null && displayChartData.size()>0){
					MenuChart menuchart=(MenuChart)displayChartData.get(0);
					List<Measures> measures = menuchart.getMenuMeasures();
					Map<String, List<String>> accountMap = service.getAccountMap(measures);
					accountMapdata = Json.stringify(Json.toJson(accountMap));
					System.out.println("accountMapdata " + accountMapdata);
					}
					int plots=0;
					for(List<Object> foreCastchart:foreCastcharts.values()){
	        		   if(!(foreCastchart.get(0) instanceof Integer)){
	        			  MenuChart chartmenu=(MenuChart)foreCastchart.get(0);
	        			  foreCastChartData[plots][0]=Json.stringify(Json.toJson(chartmenu.getCharts()));
	        			   //System.out.println("forecastdata " + plots+"   " +foreCastChartData[plots][0]);
	           		   	   if(foreCastchart.get(1)!=null){
	           		   		   foreCastChartData[plots][1] = Json.stringify(Json.toJson(foreCastchart.get(1)));   
	           		   		   System.out.println("testing work 1"  + foreCastChartData[plots][1]);
	           		   	   }	
	           		   	   if(foreCastchart.get(2)!=null){
	           		   		   //foreCastChartData[plots][1] = Json.stringify(Json.toJson(foreCastchart.get(1)));        		   
	           		   		   foreCastChartData[plots][2] = Json.stringify(Json.toJson(foreCastchart.get(2)));
							   System.out.println("testing work 2" + foreCastChartData[plots][1]);

	           		   	   }	
	           		   	   ++plots;  	
	        		   }

					}
	        	
	        	
	        	
	        	//;
	        	
	            //return ok("Feed title:" + response.asJson().findPath("title"));
	        	   String val = "";
	        	   if(!forecastParam.equals("sfp,sfp")){
	        	   String[] param1 = forecastParam.split(",");
	        	   val = param1[0]+","+param1[1];
	        	   }else {
	        		   val = forecastParam;
	        	   }
	        	   
	        	   String val2 = "";
	        	   if(!forecastParam1.equals("sfp,sfp")){
	        	   String[] param2 = forecastParam1.split(",");
	        	   val2 = param2[0]+","+param2[1];
	        	   }else {
	        		   val2 = forecastParam;
	        	   }
	        	   
	        	   String[] param1 = forecastParam.split(",");
	        	   return ok(reloadreport.render("FrontPaw",foreCastChartData,0,accountMapdata,dateval,val,val2));

	        }

		}));
	    }else {
		//	System.out.println("else of get defaultReport");
			return ok(login.render(loginForm));

	    }
		
		}catch(Exception e){
			e.printStackTrace();
			return ok(login.render(loginForm));

		}
    }    
    
	
	
    public static Result reloadSampleReport() {
		Form<Login> loginForm = form(Login.class);
		Login loginF = new Login();
		loginF.message = "";
		loginForm.fill(loginF);

    	/* Map<String, Integer> params =  new HashMap<String, Integer>();
    	String forecastParam="";
    	String forecastParam1="";
    	int dateCount=0;
		queryString = request().getQueryString("dataCount");
		dateCount = Integer.parseInt(queryString); 	
		forecastParam = request().getQueryString("forecastParams");
		forecastParam1 = request().getQueryString("forecastParams1");
		forecastParam=forecastParam.concat(","+forecastParam1);
		params.put(forecastParam, dateCount);*/
	
	
		try{
			String user = "";
		    if(session("user")!=null || session("user1")!=null){	
					long currenttime = System.currentTimeMillis();
					long accesstime = new Long(session("currenttime")).longValue();
					long time = ((currenttime - accesstime)/(1000*60));
					System.out.println("testing time " + time);
					if(time>10){
						session().clear();
						return ok(login.render(loginForm));
					}else {
						session("currenttime", new Long(currenttime).toString());
					}

			
		    	if(session("email")!=null)
		    		 user = session("email");
		    	else 
		    		 user = session("email1");
		ForecastRestService rest = new ForecastRestService();
		String forecastid = rest.getForeCastId(user);
		Hashtable authorization = new FPAuthorizationService().getAuthorization();
		String URL = "http://pmxrestenv-dev.elasticbeanstalk.com/api/forecasts/"+forecastid;
		WSRequestHolder holder =  WS.url(URL);
		holder.setHeader("Authorization", (String)authorization.get("Authorization"));
		holder.setHeader("date", (String)authorization.get("date"));
		holder.setHeader("x-fpws-authorization", (String)authorization.get("x-fpws-authorization"));
		return async(holder.get().map(new Function<WS.Response, Result>(){
	        public Result apply(WS.Response response) throws Exception {
    	        ArrayList<String> forecastnameList = new ArrayList<String>();	

	        	int dateCount=0;
	        	String forecastParam="";
	        	String forecastParam1="";

	        	dateCount = Integer.parseInt(request().getQueryString("dataCount"));
	    		forecastParam = request().getQueryString("forecastParams");
	    		forecastParam1 = request().getQueryString("forecastParams1");
	    		forecastParam=forecastParam.concat(","+forecastParam1);
	    		Map<String, Integer> params =  new HashMap<String, Integer>();
	    		params.put(forecastParam, dateCount);
/*	    		StringWriter writer = new StringWriter();
	    		IOUtils.copy(response.getBodyAsStream(), writer, "UTF-8");
	    		String body = writer.toString(); 
*/	    		String email = "";
	    		DataServiceI service = new DataServiceImpl();
	    		String [][]foreCastChartData = new String[5][5];
	    		Map<Integer, List<Object>> foreCastcharts = service.getReloadReport(params,email,response.getBodyAsStream(),forecastnameList);
					if(forecastnameList!=null && forecastnameList.size()>0){
						session("forecastname",forecastnameList.get(0));
					}	

					String dateval = "";
	    		   List<Object> displayChartData = foreCastcharts.get(0);
	    		   List<Object> dateobj = foreCastcharts.get(150);
				   if(dateobj!=null && dateobj.size()>0 ){
	    			 Integer datesize = (Integer)dateobj.get(0);
	    			  dateval = ""+datesize;
				   } 	
					
				   String 	accountMapdata = "";
				   if(displayChartData!=null && displayChartData.size()>0){
					MenuChart menuchart=(MenuChart)displayChartData.get(0);
					List<Measures> measures = menuchart.getMenuMeasures();
					Map<String, List<String>> accountMap = service.getAccountMap(measures);
					accountMapdata = Json.stringify(Json.toJson(accountMap));
					System.out.println("accountMapdata " + accountMapdata);
					}
					int plots=0;
					for(List<Object> foreCastchart:foreCastcharts.values()){
	        		   if(!(foreCastchart.get(0) instanceof Integer)){
	        			  MenuChart chartmenu=(MenuChart)foreCastchart.get(0);
	        			  foreCastChartData[plots][0]=Json.stringify(Json.toJson(chartmenu.getCharts()));
	        			   //System.out.println("forecastdata " + plots+"   " +foreCastChartData[plots][0]);
	           		   	   if(foreCastchart.get(1)!=null){
	           		   		   foreCastChartData[plots][1] = Json.stringify(Json.toJson(foreCastchart.get(1)));   
	           		   		   System.out.println("testing work 1"  + foreCastChartData[plots][1]);
	           		   	   }	
	           		   	   if(foreCastchart.get(2)!=null){
	           		   		   //foreCastChartData[plots][1] = Json.stringify(Json.toJson(foreCastchart.get(1)));        		   
	           		   		   foreCastChartData[plots][2] = Json.stringify(Json.toJson(foreCastchart.get(2)));
							   System.out.println("testing work 2" + foreCastChartData[plots][1]);

	           		   	   }	
	           		   	   ++plots;  	
	        		   }

					}
	        	
	        	
	        	
	        	//;
	        	
	            //return ok("Feed title:" + response.asJson().findPath("title"));
	        	   String val = "";
	        	   if(!forecastParam.equals("sfp,sfp")){
	        	   String[] param1 = forecastParam.split(",");
	        	   val = param1[0]+","+param1[1];
	        	   }else {
	        		   val = forecastParam;
	        	   }
	        	   
	        	   String val2 = "";
	        	   if(!forecastParam1.equals("sfp,sfp")){
	        	   String[] param2 = forecastParam1.split(",");
	        	   val2 = param2[0]+","+param2[1];
	        	   }else {
	        		   val2 = forecastParam;
	        	   }
	        	   
	        	   String[] param1 = forecastParam.split(",");
	        	   return ok(reloadreportsample.render("FrontPaw",foreCastChartData,0,accountMapdata,dateval,val,val2));

	        }

		}));
	    }else {
		//	System.out.println("else of get defaultReport");
			return ok(login.render(loginForm));

	    }
		
		}catch(Exception e){
			e.printStackTrace();
			return ok(login.render(loginForm));

		}
    }    
    
    
    
	
    /**
     * Reload report.
     *
     *Called when any date selector or forecast parameter is selected
     *
     * @param queryString the query string
     * @return the result
     * 
     */
    public static Result reloadReport1() {
		Form<Login> loginForm = form(Login.class);
		Login loginF = new Login();
		loginF.message = "";
		loginForm.fill(loginF);

    	/* Map<String, Integer> params =  new HashMap<String, Integer>();
    	String forecastParam="";
    	String forecastParam1="";
    	int dateCount=0;
		queryString = request().getQueryString("dataCount");
		dateCount = Integer.parseInt(queryString); 	
		forecastParam = request().getQueryString("forecastParams");
		forecastParam1 = request().getQueryString("forecastParams1");
		forecastParam=forecastParam.concat(","+forecastParam1);
		params.put(forecastParam, dateCount);*/
	
	
		try{
			String user = "";
		    if(session("user")!=null || session("user1")!=null){	
					long currenttime = System.currentTimeMillis();
					long accesstime = new Long(session("currenttime")).longValue();
					long time = ((currenttime - accesstime)/(1000*60));
					System.out.println("testing time " + time);
					if(time>10){
						session().clear();
						return ok(login.render(loginForm));
					}else {
						session("currenttime", new Long(currenttime).toString());
					}

					if(session("email")!=null)
						user = session("email");
					else 
						user = session("email1");
		ForecastRestService rest = new ForecastRestService();
		String forecastid = rest.getForeCastId(user);
		Hashtable authorization = new FPAuthorizationService().getAuthorization();
		String URL = "http://pmxrestenv-dev.elasticbeanstalk.com/api/forecasts/"+forecastid;
		WSRequestHolder holder =  WS.url(URL);
		holder.setHeader("Authorization", (String)authorization.get("Authorization"));
		holder.setHeader("date", (String)authorization.get("date"));
		holder.setHeader("x-fpws-authorization", (String)authorization.get("x-fpws-authorization"));
		return async(holder.get().map(new Function<WS.Response, Result>(){
	        public Result apply(WS.Response response) throws Exception {
    	        ArrayList<String> forecastnameList = new ArrayList<String>();	

	        	int dateCount=0;
	        	String forecastParam="";
	        	String forecastParam1="";

	        	dateCount = Integer.parseInt(request().getQueryString("dataCount"));
	    		forecastParam = request().getQueryString("forecastParams");
	    		forecastParam1 = request().getQueryString("forecastParams1");
	    		forecastParam=forecastParam.concat(","+forecastParam1);
	    		Map<String, Integer> params =  new HashMap<String, Integer>();
	    		params.put(forecastParam, dateCount);
/*	    		StringWriter writer = new StringWriter();
	    		IOUtils.copy(response.getBodyAsStream(), writer, "UTF-8");
	    		String body = writer.toString(); 
*/	    		String email = "";
	    		DataServiceI service = new DataServiceImpl();
	    		String [][]foreCastChartData = new String[5][5];
	    		Map<Integer, List<Object>> foreCastcharts = service.getReloadReport(params,email,response.getBodyAsStream(),forecastnameList);
					if(forecastnameList!=null && forecastnameList.size()>0){
						session("forecastname",forecastnameList.get(0));
					}	

	    
	    		   List<Object> displayChartData = foreCastcharts.get(0);
	    		   List<Object> dateobj = foreCastcharts.get(150);
				   String dateval = "";
				   	if(dateobj!=null && dateobj.size()>0 ){

	    			Integer datesize = (Integer)dateobj.get(0);
	    			 dateval = ""+datesize;
					}

					String accountMapdata = "";	
				   if(displayChartData!=null && displayChartData.size()>0){

	        	   MenuChart menuchart=(MenuChart)displayChartData.get(0);
	    		   List<Measures> measures = menuchart.getMenuMeasures();
	        	   Map<String, List<String>> accountMap = service.getAccountMap(measures);
	        	   accountMapdata = Json.stringify(Json.toJson(accountMap));
				   }
	        	   int plots=0;
	        	   for(List<Object> foreCastchart:foreCastcharts.values()){
	        		   if(!(foreCastchart.get(0) instanceof Integer)){
	        			  MenuChart chartmenu=(MenuChart)foreCastchart.get(0);
	        			  foreCastChartData[plots][0]=Json.stringify(Json.toJson(chartmenu.getCharts()));
	        			  //System.out.println("forecastdata " + plots+"   " +foreCastChartData[plots][0]);
	           		   	   if(foreCastchart.get(1)!=null){
	           		   		   foreCastChartData[plots][1] = Json.stringify(Json.toJson(foreCastchart.get(1)));   
	           		   		 //  System.out.println("testing work" + foreCastChartData[plots][1]);
	           		   	   }	
	           		   	   if(foreCastchart.get(2)!=null){
	           		   		   //foreCastChartData[plots][1] = Json.stringify(Json.toJson(foreCastchart.get(1)));        		   
	           		   		   foreCastChartData[plots][2] = Json.stringify(Json.toJson(foreCastchart.get(2)));
	           		   	   }	
	           		   	   ++plots;  	
	        		   }

	        	   }
	        	
	        	
	        	
	        	//;
	        	
	            //return ok("Feed title:" + response.asJson().findPath("title"));
	        	   String val = "";
	        	   if(!forecastParam.equals("sfp,sfp")){
	        	   String[] param1 = forecastParam.split(",");
	        	   val = param1[0]+","+param1[1];
	        	   }else {
	        		   val = forecastParam;
	        	   }
	        	   
	        	   String val2 = "";
	        	   if(!forecastParam1.equals("sfp,sfp")){
	        	   String[] param2 = forecastParam1.split(",");
	        	   val2 = param2[0]+","+param2[1];
	        	   }else {
	        		   val2 = forecastParam;
	        	   }
	        	   
	        	   String[] param1 = forecastParam.split(",");
	        	   return ok(reloadreport1.render("FrontPaw",foreCastChartData,0,accountMapdata,dateval,val,val2));

	        }

		}));
	    }else {
		//	System.out.println("else of get defaultReport");
			return ok(login.render(loginForm));

	    }
		
		}catch(Exception e){
			e.printStackTrace();
			return ok(login.render(loginForm));

		}
    }    
    	
    
    
/*    public static Result reloadReport(String queryString) {
    	Map<String, Integer> params =  new HashMap<String, Integer>();
    	String forecastParam="";
    	String forecastParam1="";
    	int dateCount=0;
    	
    	queryString = request().getQueryString("dataCount");
    	dateCount = Integer.parseInt(queryString); 	
    	forecastParam = request().getQueryString("forecastParams");
    	forecastParam1 = request().getQueryString("forecastParams1");
    	forecastParam=forecastParam.concat(","+forecastParam1);
    	params.put(forecastParam, dateCount);
    	
    	
    	String email = session("email");
    	
    	InputStream input = null;

    	DataServiceI service = new DataServiceImpl();
 	   	String [][]foreCastChartData = new String[5][5];
 	   	Map<Integer, List<Object>> foreCastcharts = service.getReport(params,email,input);
 	   
 	   List<Object> displayChartData = foreCastcharts.get(0);
	   MenuChart menuchart=(MenuChart)displayChartData.get(0);
	   List<Measures> measures = menuchart.getMenuMeasures();
	   Map<String, List<String>> accountMap = service.getAccountMap(measures);
	   String accountMapdata = Json.stringify(Json.toJson(accountMap));
	   int plots=0;
	   
	   for(List<Object> foreCastchart:foreCastcharts.values()){       		   
		   MenuChart chartmenu=(MenuChart)foreCastchart.get(0);
   		   foreCastChartData[plots][0]=Json.stringify(Json.toJson(chartmenu.getCharts()));        		   
		   foreCastChartData[plots][1] = Json.stringify(Json.toJson(foreCastchart.get(1)));        		   
		   foreCastChartData[plots][2] = Json.stringify(Json.toJson(foreCastchart.get(2)));
		   ++plots;  		   

	   }
       	   
       	   return ok(
                      report.render("FrontPaw",foreCastChartData,0, accountMapdata)
                  );
       
   }*/
  
    
  
   public static Result logout() {
		session().clear();
    	Form<Login> loginForm = form(Login.class);
    	Login loginF = new Login();
    	loginF.message = "";
    	loginForm.fill(loginF);
        return ok(login.render(loginForm));
   }
  
  
}
