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

import io.swagger.v3.oas.annotations.Hidden;
import ua.com.foxminded.model.dto.TeacherDto;
import ua.com.foxminded.model.enums.Month;
import ua.com.foxminded.service.interfaces.TeacherService;

@Controller
@RequestMapping("/teachers")
@Hidden
public class TeachersController {
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
    
    @Autowired
    TeacherService teacherService;
    
    Logger logger = LoggerFactory.getLogger("SampleLogger");
    
    @GetMapping()
    public ModelAndView findAllTeachers() {
 
        List<TeacherDto> teachers = teacherService.findAllTeacher();
       
        ModelAndView teacherMV = new ModelAndView("teachers");
        teacherMV.addObject("teachers", teachers);
        
        List<String> months = Month.getAllMonths();
        
        teacherMV.addObject("months", months);
        return teacherMV;   
    }
    @GetMapping("/new")
    public ModelAndView addNewTeacher(@ModelAttribute("teacher") TeacherDto teacher) {

        ModelAndView teacherMV = new ModelAndView("newTeacher");

        return teacherMV;
    }
    
    @GetMapping("/{uuid}")
    public ModelAndView showTeacher(@PathVariable("uuid") String uuid) {

        TeacherDto teacher = teacherService.findTeacher(UUID.fromString(uuid));
        ModelAndView teacherMV = new ModelAndView("editTeacher");
        teacherMV.addObject("teacher", teacher);

        return teacherMV;
    }
    
    @PostMapping()
    public ModelAndView createTeacher(@ModelAttribute("teacher") @Valid TeacherDto teacher, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            logger.info("BINDING RESuLT ERROR");
            bindingResult.getFieldErrors().forEach(error ->
            {
                logger.info(error.getField() + " " + error.getDefaultMessage());
            });
            ModelAndView teacherMV = new ModelAndView("newTeacher");
            return teacherMV;
        }   
        teacherService.addTeacher(teacher);
        ModelAndView teacherMV = new ModelAndView("redirect:/teachers");
 
        return teacherMV;
    }
    
    @PostMapping("/edit")
    public ModelAndView editTeacher(@ModelAttribute("teacher") @Valid TeacherDto teacher, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.info("BINDING RESuLT ERROR");
            bindingResult.getFieldErrors().forEach(error ->
            {
                logger.info(error.getField() + " " + error.getDefaultMessage());
            });
            ModelAndView teacherMV = new ModelAndView("editTeacher");
            return teacherMV;
        }   
        teacherService.editTeacher(teacher, teacher.getIdPerson());
        ModelAndView teacherMV = new ModelAndView("redirect:/teachers");
 
        return teacherMV;
    }
    
    @GetMapping("/delete/{uuid}")
    public ModelAndView deleteTeacher(@PathVariable("uuid") String uuid) {

        teacherService.deleteTeacher(UUID.fromString(uuid));
        ModelAndView teacherMV = new ModelAndView("redirect:/teachers");
 
        return teacherMV;
    }  
}

