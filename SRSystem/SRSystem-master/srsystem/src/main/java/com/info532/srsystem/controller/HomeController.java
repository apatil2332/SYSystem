package com.info532.srsystem.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.info532.srsystem.entity.Class;
import com.info532.srsystem.entity.Course;
import com.info532.srsystem.entity.PrerequisiteId;
import com.info532.srsystem.entity.Student;
import com.info532.srsystem.entity.StudentForm;
import com.info532.srsystem.service.ClassService;
import com.info532.srsystem.service.CourseCreditService;
import com.info532.srsystem.service.CoursesService;
import com.info532.srsystem.service.GEnrollmentService;
import com.info532.srsystem.service.LogService;
import com.info532.srsystem.service.PrerequisiteService;
import com.info532.srsystem.service.ScoreGradeService;
import com.info532.srsystem.service.StudentService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HomeController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private ClassService classService;

    @Autowired
    private PrerequisiteService prerequisiteService;

    @Autowired
    private GEnrollmentService gEnrollmentService;

    @Autowired
    private CourseCreditService courseCreditService;

    @Autowired
    private ScoreGradeService scoreGradeService;

    @Autowired
    private LogService logService;


    @GetMapping("/home")
    public String home(@RequestParam(value = "action", required = false) String action, Model model) {
        
        String selectedOption = "";
        if(action != null){
            switch (action) {
                case "students":
                    selectedOption = "students";
                    model.addAttribute("students", studentService.showStudents());
                    model.addAttribute("student", new StudentForm());
                    break;
                case "courses":
                    selectedOption = "courses";
                    model.addAttribute("courses", coursesService.showCourses());
                    model.addAttribute("course", new Course());
                    break;
                case "classes":
                    selectedOption = "classes";
                    model.addAttribute("classes", classService.showClasses());
                    model.addAttribute("class", new Class());
                    break;
                case "courseCredits":
                    selectedOption = "courseCredits";
                    model.addAttribute("courseCredits", courseCreditService.showCourseCredits());
                    break;
                case "studentGrades":
                    selectedOption = "studentGrades";
                    model.addAttribute("studentGrades", scoreGradeService.showScoreGrades());
                    break;
                case "graduateStudentEnrollment":
                    selectedOption = "graduateStudentEnrollment";
                    model.addAttribute("graduateStudentEnrollment", gEnrollmentService.showGEnrollments());
                    break;
                case "ViewStudentsFromClass":
                    selectedOption = "ViewStudentsFromClass";
                    model.addAttribute("enrollments", gEnrollmentService.showGEnrollments());
                    break;
                case "coursePrerequisites":
                    selectedOption = "coursePrerequisites";
                    model.addAttribute("coursePrerequisites", prerequisiteService.showPrerequites());
                    break;
                case "graduateStudentEnrollmentDrop":
                    selectedOption = "graduateStudentEnrollmentDrop";
                    model.addAttribute("gEnrollments", gEnrollmentService.showGEnrollments());
                    model.addAttribute("showEnrollDropContainer", true);
                    break;
                case "checkLogs":
                    selectedOption = "checkLogs";
                    model.addAttribute("logs", logService.showLogs());
                    break;
                default:
                    break;
            }
        }
        
        model.addAttribute("selectedOption", selectedOption);
        return "home";
    }

    @GetMapping("/students")
    public String getStudents(Model model) {
        model.addAttribute("students", studentService.showStudents());
        return "home";
    }

    @GetMapping("/courses")
    public String getCourses(Model model) {
        model.addAttribute("courses", coursesService.showCourses());
        return "home";
    }

    @GetMapping("/getStudentsInClass")
    public String getStudentsInClass(@RequestParam("classId") String classId, Model model) {
        model.addAttribute("students", classService.getStudentsInClass(classId));
        return "home";
    }

    @GetMapping("/getPrereq")
    public String getPrereq(@RequestParam("deptCode") String deptCode, @RequestParam("courseNumber") Integer courseNumber, Model model) {
        model.addAttribute("prerequisites", prerequisiteService.getPrerequisitesByDeptCodeAndCourseNumber(deptCode, courseNumber));
        return "test4";
    }

    @PostMapping("/enroll")
    public String enroll(@RequestParam("buNo") String bNumber, @RequestParam("classNo") String classNo, Model model) {
        model.addAttribute("msg", gEnrollmentService.enroll(bNumber.toUpperCase(), classNo.toLowerCase()));
        model.addAttribute("buNo", bNumber);
        model.addAttribute("classNo", classNo);
        model.addAttribute("selectedOption", "graduateStudentEnrollmentDrop");
        model.addAttribute("showEnrollDropContainer", true);
        model.addAttribute("gEnrollments", gEnrollmentService.showGEnrollments());

        return "home";
    }
    

    @PostMapping("/dropenroll")
    public String dropEnroll(@RequestParam("bNumber") String bNumber, @RequestParam("classId") String classId, Model model) {
        model.addAttribute("msg", gEnrollmentService.dropEnroll(bNumber, classId.toLowerCase()));
        model.addAttribute("selectedOption", "graduateStudentEnrollmentDrop");
        model.addAttribute("showEnrollDropContainer", true);
        model.addAttribute("gEnrollments", gEnrollmentService.showGEnrollments());
        return "home";
    }

    @PostMapping("/deleteStudent")
    public String deleteStudent(@RequestParam("bNumber") String bNumber, Model model) {
        model.addAttribute("msg", studentService.dropStudent(bNumber));
        model.addAttribute("students", studentService.showStudents());
        model.addAttribute("selectedOption", "students");
        model.addAttribute("student", new StudentForm());
        return "home";
    }

    @PostMapping("/deleteCourse")
    public String deleteCourse(@RequestParam("dept_code") String deptCode, @RequestParam("courseNo") Integer courseNo, Model model) {
        
        boolean isDeleted = coursesService.deleteCourseByDeptCodeAndCourseNo(deptCode, courseNo);
        if(isDeleted){
            model.addAttribute("msg", "Course "+ courseNo +" by department "+ deptCode + "Deleted Successfully");
        }else{
            model.addAttribute("msg", "Error: Cannot delete course: associated classes exist.");
        }
        
        model.addAttribute("courses", coursesService.showCourses());
        model.addAttribute("course", new Course());
        model.addAttribute("selectedOption", "courses");

        return "home";
    }

    @PostMapping("/deleteClass")
    public String deleteClass(@RequestParam("classId") String classId, Model model) {
        
        boolean isDeleted = classService.deleteClassById(classId);
        if(isDeleted){
            model.addAttribute("msg", "Class "+ classId +"Deleted Successfully");
        }else{
            model.addAttribute("msg", "Error: Could Not Delete Class");
        }
        
        model.addAttribute("classes", classService.showClasses());
        model.addAttribute("class", new Class());
        model.addAttribute("selectedOption", "classes");

        return "home";
    }

    @PostMapping("/viewStudentsFromClass")
    public String viewStudentsFromClass(
        @RequestParam("classId") String classId,
        Model model) {
            model.addAttribute("classId", classId);
            model.addAttribute("selectedOption", "ViewStudentsFromClass");
            model.addAttribute("studentsInClass", classService.getStudentsInClass(classId));

            return "home";
    }

    @PostMapping("/searchCoursePrerequisites")
    public String searchCoursePrerequisites(
        @RequestParam("deptCode") String deptCode,
        @RequestParam("courseNo") Integer courseNo,
        Model model) {
            model.addAttribute("deptCode", deptCode);
            model.addAttribute("courseNo", courseNo);
            model.addAttribute("selectedOption", "searchCoursePrerequisites");

            List<PrerequisiteId> prerequisites = prerequisiteService.getPrerequisitesByDeptCodeAndCourseNumber(deptCode.toUpperCase(), courseNo);
            if(prerequisites.isEmpty()){
                model.addAttribute("msg", "No Prerequisites Found");
            }else{
                model.addAttribute("viewCoursePrerequisites", prerequisites);
            }
            

            return "home";
    }


    @PostMapping("/addStudent")
    public String addStudent(StudentForm studentForm, Model model) {
        try {
            // Convert StudentForm to Student
            Student student = new Student();
            student.setBNumber(studentForm.getBNumber());
            student.setFirstName(studentForm.getFirstName());
            student.setLastName(studentForm.getLastName());
            student.setStudentLevel(studentForm.getStudentLevel()); // Assuming degree is equivalent to student level
            student.setGpa(Double.parseDouble(studentForm.getGpa()));
            student.setEmail(studentForm.getEmail());
            // Convert birthDate String to Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate = dateFormat.parse(studentForm.getBirthDate());
            student.setBirthDate(birthDate);

            studentService.saveStudent(student);
            model.addAttribute("msg", "Student Added Successfully");
            model.addAttribute("selectedOption", "students");
            model.addAttribute("students", studentService.showStudents());
            model.addAttribute("student", new StudentForm());

        } catch (Exception e) {
            model.addAttribute("msg", "ERROR WHILE CREATING NEW STUDENT");
        }

        return "home";
    }


    @PostMapping("/addCourse")
    public String addCourse(Course course, Model model) {
        try{
            coursesService.save(course);
            model.addAttribute("msg", "Course Added Successfully");
            model.addAttribute("selectedOption", "courses");
            model.addAttribute("courses", coursesService.showCourses());
            model.addAttribute("course", new Course());

        }catch(Exception e){
            model.addAttribute("msg", "ERROR WHILE CREATING NEW COURSE");
        }

        return "home";
    }

    @PostMapping("/addClass")
    public String addClass(Class clas, Model model) {
        try{
            classService.save(clas);
            model.addAttribute("msg", "Class Added Successfully");
            model.addAttribute("selectedOption", "classes");
            model.addAttribute("classes", classService.showClasses());
            model.addAttribute("class", new Class());

        }catch(Exception e){
            model.addAttribute("msg", "ERROR WHILE CREATING NEW CLASS");
        }

        return "home";
    }

    

}