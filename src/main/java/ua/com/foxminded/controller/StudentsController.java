package ua.com.foxminded.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ua.com.foxminded.model.dto.GroupDto;
import ua.com.foxminded.model.dto.StudentDto;
import ua.com.foxminded.model.enums.Month;
import ua.com.foxminded.service.interfaces.GroupService;
import ua.com.foxminded.service.interfaces.StudentService;

@Controller
@RequestMapping("/students")
public class StudentsController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Autowired
    StudentService studentService;

    @Autowired
    GroupService groupService;

    Logger logger = LoggerFactory.getLogger("SampleLogger");
    
    @GetMapping()
    public ModelAndView findAllStudent() {

        List<StudentDto> students = studentService.findAllStudent();

        ModelAndView studentMV = new ModelAndView("students");
        studentMV.addObject("students", students);
        List<String> months = Month.getAllMonths();

        studentMV.addObject("months", months);
        return studentMV;
    }

    @GetMapping("/new")
    public ModelAndView addNewStudent(@ModelAttribute("student") StudentDto student) {

        ModelAndView studentMV = new ModelAndView("newStudent");
        List<GroupDto> groups = groupService.findAllGroups();

        studentMV.addObject("groups", groups);

        return studentMV;
    }

    @PostMapping()
    public ModelAndView createStudent(@ModelAttribute("student") @Valid StudentDto student,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.info("BINDING RESuLT ERROR");
            bindingResult.getFieldErrors().forEach(error ->
            {
                logger.info(error.getField() + " " + error.getDefaultMessage());
            });
           
            ModelAndView studentMV = new ModelAndView("newStudent");
            List<GroupDto> groups = groupService.findAllGroups();
            studentMV.addObject("groups", groups);
            return studentMV;
        }
        studentService.addStudent(student);
        ModelAndView studentMV = new ModelAndView("redirect:/students");

        return studentMV;
    }

    @GetMapping("/{uuid}")
    public ModelAndView showStudent(@PathVariable("uuid") String uuid) {

        StudentDto student = studentService.findStudent(UUID.fromString(uuid));
        ModelAndView studentMV = new ModelAndView("editStudent");
        studentMV.addObject("student", student);
        
        List<GroupDto> groups = groupService.findAllGroups();
        studentMV.addObject("groups", groups);
        return studentMV;
    }

    @PostMapping("/edit")
    public ModelAndView editStudent(@ModelAttribute("student") @Valid StudentDto student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.info("BINDING RESuLT ERROR");
            bindingResult.getFieldErrors().forEach(error ->
            {
                logger.info(error.getField() + " " + error.getDefaultMessage());
            });
            ModelAndView studentMV = new ModelAndView("editStudent");
            studentMV.addObject("student", student);
            List<GroupDto> groups = groupService.findAllGroups();
            studentMV.addObject("groups", groups);
            return studentMV;
        }
        studentService.editStudent(student, student.getIdPerson());
        ModelAndView studentMV = new ModelAndView("redirect:/students");
        
        
        return studentMV;
    }

    @GetMapping("/delete/{uuid}")
    public ModelAndView deleteStudent(@PathVariable("uuid") String uuid) {

        studentService.deleteStudent(UUID.fromString(uuid));
        ModelAndView studentMV = new ModelAndView("redirect:/students");

        return studentMV;
    }
}
