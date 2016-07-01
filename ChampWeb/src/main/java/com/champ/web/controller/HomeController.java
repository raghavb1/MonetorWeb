package com.champ.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/** This controller is called after user authentication to redirect user to its home page **/

@Controller
public class HomeController {

	/** Redirects to home page **/
	@RequestMapping("/home")
	public String getHome(@RequestParam(value="message",required=false) String message,ModelMap map)
	{
		if(message != null)
		{
			map.put("message", message);
		}
		return "home";
	}
	
	/** Redirects to access denied page **/
	@RequestMapping("/error")
	public String getErrorPage()
	{
		return "error";
	}
	
	/*public static void main(String args[]){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();  
		String hashedPassword = passwordEncoder.encode("testing");
		System.out.println(hashedPassword);
	}*/

}
