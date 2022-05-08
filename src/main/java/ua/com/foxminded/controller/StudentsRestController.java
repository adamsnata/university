package ua.com.foxminded.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import ua.com.foxminded.error.ErrorDescriptor;
import ua.com.foxminded.model.dto.StudentDto;
import ua.com.foxminded.service.interfaces.GroupService;
import ua.com.foxminded.service.interfaces.StudentService;

@RestController
public class StudentsRestController  {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Autowired
    StudentService studentService;

    @Autowired
    GroupService groupService;

    Logger logger = LoggerFactory.getLogger("SampleLogger");
    
    @GetMapping(value = "/students",
            produces = {"application/json"})
    public ResponseEntity<List<StudentDto>> findAllStudent() {

        List<StudentDto> students = studentService.findAllStudent();

        return students != null &&  !students.isEmpty()
                ? new ResponseEntity(students, HttpStatus.OK)
                        : new ResponseEntity(new ErrorDescriptor().setStatus(HttpStatus.NOT_FOUND).setMessage("Not found Students "),
                                HttpStatus.NOT_FOUND);
    }


    @PostMapping(value = "/student",
            produces = {"application/json"},
            consumes = {"application/json"})
        public ResponseEntity createStudent(@Valid @RequestBody StudentDto student,
                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new ErrorDescriptor(bindingResult, "student"), HttpStatus.BAD_REQUEST) ;
        }
           
        if (studentService.addStudent(student)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    
        return new ResponseEntity(new ErrorDescriptor().setStatus(HttpStatus.NOT_FOUND).setMessage("Not create Student "),
                HttpStatus.NOT_FOUND); 
    }

    @GetMapping(value = "/student/{uuid}",
            produces = {"application/json"})
    public ResponseEntity<StudentDto> showStudent(@PathVariable("uuid") String uuid) {

        
        StudentDto student = studentService.findStudent(UUID.fromString(uuid));
        
        return (student != null && student.getIdPerson()!=null)
                ? new ResponseEntity<>(student, HttpStatus.OK)
                        : new ResponseEntity(new ErrorDescriptor().setStatus(HttpStatus.NOT_FOUND).setMessage("Not found Student "),
                                HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/student/{uuid}" ,
            produces = {"application/json"},
            consumes = {"application/json"})   
    public ResponseEntity editStudent(@PathVariable(name = "uuid") String uuid, 
            @Valid @RequestBody StudentDto student ,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new ErrorDescriptor(bindingResult, "student"), HttpStatus.BAD_REQUEST) ;
        }
        
        StudentDto studentCheck = studentService.findStudent(UUID.fromString(uuid));
        if (studentCheck.getIdPerson() == null) {
            return new ResponseEntity(new ErrorDescriptor().setStatus(HttpStatus.NOT_FOUND).setMessage("Not find Student with id  " + uuid),
                    HttpStatus.NOT_FOUND);
        }
             
        studentService.editStudent(student, UUID.fromString(uuid));
        
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/student/{uuid}", 
            produces = {"application/json"})
    public ResponseEntity deleteStudent(@PathVariable("uuid") String uuid) {
        
        StudentDto studentCheck = studentService.findStudent(UUID.fromString(uuid));
        if (studentCheck.getIdPerson() == null) {
            return new ResponseEntity(new ErrorDescriptor().setStatus(HttpStatus.NOT_FOUND).setMessage("Not find Student with id  " + uuid),
                    HttpStatus.NOT_FOUND); 
        }
        studentService.deleteStudent(UUID.fromString(uuid));
        studentCheck = studentService.findStudent(UUID.fromString(uuid));
        if (studentCheck.getIdPerson() != null) {
            return new ResponseEntity(new ErrorDescriptor().setStatus(HttpStatus.NOT_IMPLEMENTED).setMessage("Do not delete Student with id  " + uuid),
                    HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
