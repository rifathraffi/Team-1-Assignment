package com.ashwin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

import com.ashwin.accessingdatamysql.Employee;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;

import com.ashwin.accessingdatamysql.EmployeeRepository;
import com.ashwin.accessingdatamysql.Person;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;
	
//	@GetMapping("/list")
//	String listall(Model model) {
//		
//		model.addAttribute("employeeDetails", employeeRepository.findAll());
//
//		return "list-employees";
//		
//		
//////		ModelAndView mav = new ModelAndView("list-employees");
//////		mav.addObject("employees", employeeRepository.findAll());
////		return "list";
//	}
	
	@PostMapping("/getemp")
	public String empdetails(@RequestParam(name = "empid", required = true) Integer empid,  Model model) {


		model.addAttribute("employeeDetails", employeeRepository.findAllByEmpid(empid));

		return "list-employees";
		
	
}
	
	 
}