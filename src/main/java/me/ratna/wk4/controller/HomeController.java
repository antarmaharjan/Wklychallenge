package me.ratna.wk4.controller;

import me.ratna.wk4.models.Education;
import me.ratna.wk4.models.Job;
import me.ratna.wk4.models.Person;
import me.ratna.wk4.models.Skill;
import me.ratna.wk4.repositories.EducationRepository;
import me.ratna.wk4.repositories.JobRepository;
import me.ratna.wk4.repositories.PersonRepository;
import me.ratna.wk4.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
import javax.validation.Valid;

import java.util.ArrayList;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
public class HomeController {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    EducationRepository educationRepository;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    SkillRepository skillRepository;

    @RequestMapping("/login")
    public String logon(){
        return"login";
    }
    @GetMapping("/")
    public String index(Model toSend){
        toSend.addAttribute("newPerson", new Person());
        return "index";
    }
    @PostMapping("/")
    public String index(@Valid @ModelAttribute("newPerson") Person newPerson, Model toSend, BindingResult result){
        System.out.println(result.toString());
        if(result.hasErrors()){
            return"index";
        }
        personRepository.save(newPerson);
        return"confirmperson";
    }
    @GetMapping("/addeducation")
    public String addEducation(Model toSend){

        toSend.addAttribute("anEducation", new Education());
        return "addeducation";
    }
    @PostMapping("/addeducation")
    public String confirmEducation(@Valid @ModelAttribute("anEducation") Education anEducation,Model toSend, BindingResult result){
        if(result.hasErrors()){
            return "addeducation";
        }
        educationRepository.save(anEducation);
        //additional line
        toSend.addAttribute("numberOfEdu",educationRepository.count());
        return "confirmeducation";
    }
    @GetMapping("/addjob")
    public String addwork(Model toSend,@ModelAttribute("newPerson") Person newPerson){
        toSend.addAttribute("aJob", new Job());
        return "addjob";
    }
    @PostMapping("/addjob")
    public String confirmWorkAndAddMore(@Valid @ModelAttribute("aJob") Job aJob, @ModelAttribute("newPerson") Person newPerson, BindingResult result){
        if(result.hasErrors()){
            return "addjob";
        }
        jobRepository.save(aJob);
        return "confirmjob";
    }

    @GetMapping("/addskill")
    public String addskill(Model toSend,@ModelAttribute("newPerson") Person newPerson){
        toSend.addAttribute("aSkill", new Skill());

        return "addskill";
    }
    @PostMapping("/addskill")
    public String confirmSkillAndAddMore(@Valid @ModelAttribute("aSkill") Skill aSkill, @ModelAttribute("newPerson") Person newPerson, BindingResult result){
        if(result.hasErrors()) {
            return "addskill";
        }
        skillRepository.save(aSkill);
        return "confirmskill";
    }
    @GetMapping("/generateresume")
    public String generateResume(Model toSend){

        Person person = personRepository.findById(1);
        System.out.println("name is " + person.getName());
        toSend.addAttribute("myPerson",person);

        Iterable<Education> edu = educationRepository.findAll();
        toSend.addAttribute("myEducation", edu);
        Iterable<Job> job = jobRepository.findAll();
        toSend.addAttribute("myWork", job);
        Iterable<Skill> skill = skillRepository.findAll();
        toSend.addAttribute("mySkills",skill);
        return "generateresume";
    }
    //For delete and edit
    @GetMapping("/addjob/update/{id}")
    public String defaultRequest4 (@PathVariable ("id") long id,Model model){
        Job job =jobRepository.findOne(id);
        model.addAttribute("aJob",job);
        return "addjob";
    }
    @GetMapping("/addjob/delete/{id}")
    public String delete(@PathVariable("id") long id){
        jobRepository.delete(id);
        return "redirect:/addskill";
    }
    @GetMapping("/index/update/{id}")
    public String updatePerson (@PathVariable ("id") long id,Model model){
        Person person =personRepository.findOne(id);
        model.addAttribute("newPerson",person);
        return "index";
    }
//    ------------------------------------------------------------------------------------
    @GetMapping("/updateeducation/{id}")
    public String updateEducation(@PathVariable("id") long id, Model model){
        model.addAttribute("anEducation", educationRepository.findOne(id));
        return "addeducation";
    }
    @GetMapping("/deleteeducation/{id}")
    public String deleteEducation(@PathVariable("id") long id){
        educationRepository.delete(id);
        return "redirect:/generateresume";
    }

//    -----------------------------------------------------------------------------------------



}