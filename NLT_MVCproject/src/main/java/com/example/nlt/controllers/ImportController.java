package com.example.nlt.controllers;

import com.example.nlt.services.CompanyService;
import com.example.nlt.services.EmployeeService;
import com.example.nlt.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
public class ImportController {
    private final CompanyService companyService;
    private final ProjectService projectService;
    private final EmployeeService employeeService;

    @Autowired
    public ImportController(CompanyService companyService, ProjectService projectService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/import/xml")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("xml/import-xml");
        boolean[] areImported = new boolean[3];
        areImported[0] = companyService.areImported();
        areImported[1] = projectService.areImported();
        areImported[2] = employeeService.areImported();

        modelAndView.addObject("areImported", areImported);

        return modelAndView;
    }

    @GetMapping("/import/companies")
    public ModelAndView showImportCompaniesView() throws IOException {
        ModelAndView modelAndView = new ModelAndView("xml/import-companies");

        String companies = this.companyService.readCompaniesFromFile();

        modelAndView.addObject("companies", companies);

        return modelAndView;
    }

    @PostMapping("/import/companies")
    public String importCompanies() throws JAXBException, FileNotFoundException {
        String result = this.companyService.importCompanies();

        System.out.println(result);

        return "redirect:/import";
    }

    @GetMapping("/import/projects")
    public ModelAndView showImportProjectsView() throws IOException {
        ModelAndView modelAndView = new ModelAndView("xml/import-projects");

        String projects = this.projectService.readProjectsFromFile();

        modelAndView.addObject("projects", projects);

        return modelAndView;
    }

    @PostMapping("/import/projects")
    public String importProjects() throws JAXBException, FileNotFoundException {
        String result = this.projectService.importProjects();

        System.out.println(result);

        return "redirect:/import";
    }

    @GetMapping("/import/employees")
    public ModelAndView showImportEmployeesView() throws IOException {
        ModelAndView modelAndView = new ModelAndView("xml/import-employees");

        String employees = this.employeeService.readEmployeesFromFile();

        modelAndView.addObject("employees", employees);

        return modelAndView;
    }

    @PostMapping("/import/employees")
    public String importEmployees() throws JAXBException, FileNotFoundException {
        String result = this.employeeService.importEmployees();

        System.out.println(result);

        return "redirect:/import";
    }

}
