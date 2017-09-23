package com.example.tutorial3.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tutorial3.model.StudentModel;
import com.example.tutorial3.service.InMemoryStudentService;
import com.example.tutorial3.service.StudentService;

@Controller
public class StudentController
{
	private final StudentService studentService;

	public StudentController ()
	{
		studentService = new InMemoryStudentService();
	}

@RequestMapping("/student/add")
public String add(
 
 @RequestParam(value = "name", required = true) String name,
 @RequestParam(value = "npm",required = true) String npm,
 @RequestParam(value = "gpa", required = true) double gpa)
 {
	StudentModel student = new StudentModel(name, npm, gpa);
	studentService.addStudent(student);
	return "add";
 }


@RequestMapping("/student/view")
public String view (Model model, @RequestParam(value = "npm", required = true) String npm)
	{
		StudentModel student = studentService.selectStudent(npm);
		model.addAttribute("student", student);
		return "view";
	}


@RequestMapping("/student/viewall")
public String viewAll (Model model)
	{
		List<StudentModel> students = studentService.selectAllStudents();
		model.addAttribute("students", students);
		return "viewall";
	}


@RequestMapping("/student/view/{npm}")
public String viewNpm(Model model, @PathVariable String npm) {
	
	StudentModel student = studentService.selectStudent(npm);
	if(student != null) {
		model.addAttribute("student", student);
		return "view";
	}
	else {
		return "view-not-found";
	}
}
	

@RequestMapping("/student/delete/{npm}")
public String delete(Model model, @PathVariable String npm) {
	
	StudentModel student = studentService.selectStudent(npm);
	if(student != null) {
		studentService.deleteStudent(npm);
		return "delete";
	}
	
	else {
		return "delete-not-found";
	}
	
}


}