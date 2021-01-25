package progettoIngegneriaBianchi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;
import java.time.ZoneId;


import java.util.Date;

@Controller
public class AppController {

    @Autowired
    private PatientRepository repository;

    @Autowired
    private CartellaRepository repositoryCartelle;

    @RequestMapping("/")
    public String index(){
        return "redirect:/list";
    }

    @RequestMapping("/list")
    public String list(Model model){

        List<Patient> present = new LinkedList<>();
        List<Patient> discharged = new LinkedList<>();
        List<Patient> toSearch = (List<Patient>) repository.findAll();

        model.addAttribute("patients", present);
        model.addAttribute("discharged", discharged);

        sortPatients(present,discharged, toSearch);

        return "list";
    }

    @RequestMapping("/search")
    public String search(@RequestParam(name="lastName", required=true) String lastName,
                         Model model)
    {
        List<Patient> present = new LinkedList<>();
        List<Patient> discharged = new LinkedList<>();
        List<Patient> toSearch = repository.findByLastName(lastName);

        if (lastName=="")
            return "redirect:/list";

        sortPatients(present,discharged,toSearch);

        model.addAttribute("patients", present);
        model.addAttribute("discharged", discharged);

        return "list";
    }

    public void sortPatients(List<Patient> present, List<Patient> discharged, List<Patient> toSearch){
        for (Patient p: toSearch){
            if(p.getDischargeDate() == null)
                present.add(p);
            else if (p.getDischargeDate()!=null &&  p.getDischargeDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().isAfter(LocalDateTime.now()))
                present.add(p);
            else
                discharged.add(p);
        }
    }


    @RequestMapping("/input")
    public String input(){
        return "input";
    }

    @RequestMapping("/create")
    public String create(
            @RequestParam(name="firstName", required=true) String firstName,
            @RequestParam(name="lastName", required=true) String lastName,
            @RequestParam(name="admissionDate", required=true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date admissionDate
    ){

                Patient patient = repository.save(new Patient(firstName, lastName, admissionDate));
                Cartella cartella = repositoryCartelle.save(new Cartella(patient.getId(), "",""));
                int prova = 0;
                return "redirect:/list";
    }


    @RequestMapping("/cartella")
    public String cartella(
            @RequestParam(name="id", required=true) Long id,
            Model model) {
        Optional<Patient> patient = repository.findById(id);
        Optional<Cartella> cartella = repositoryCartelle.findById(id);
        int flag = 0;
        if(patient.isPresent()) {
            if(patient.get().getDischargeDate() == null)
                flag = 1;
            if ((patient.get().getDischargeDate()!=null && patient.get().getDischargeDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().isAfter(LocalDateTime.now())))
                flag = 1;

            model.addAttribute("patient", patient.get());
            model.addAttribute("cartella", cartella.get());
            model.addAttribute("flag", flag);
            return "cartella";
        }
        else
            return "notfound";
    }


    @RequestMapping("/edit")
    public String edit(
            @RequestParam(name="id", required=true) Long id,
            Model model) {
        Optional<Patient> patient = repository.findById(id);
        Optional<Cartella> cartella = repositoryCartelle.findById(id);
        if(patient.isPresent()) {
            model.addAttribute("patient", patient.get());
            model.addAttribute("cartella", cartella.get());
            return "edit";
        }
        else
            return "notfound";
    }

    @RequestMapping("/update")
    public String update(
            @RequestParam(name="id", required=true) Long id,
            @RequestParam(name="firstName", required=true) String firstname,
            @RequestParam(name="lastName", required=true) String lastname,
            @RequestParam(name="admissionDate", required=true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date admissionDate,
            @RequestParam(name="dischargeDate", required=true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dischargeDate,
            @RequestParam(name="drugs", required=true) String drugs,
            @RequestParam(name="prescription", required=true) String prescription,
            Model model) {
        Optional<Patient> patient = repository.findById(id);
        Optional<Cartella> cartella = repositoryCartelle.findById(id);
        if(patient.isPresent()) {
            patient.get().setFirstName(firstname);
            patient.get().setLastName(lastname);
            patient.get().setAdmissionDate(admissionDate);
            patient.get().setDischargeDate(dischargeDate);
            cartella.get().setDrugs(drugs);
            cartella.get().setPrescription(prescription);
            repository.save(patient.get());
            repositoryCartelle.save(cartella.get());
            return "redirect:/list";
        }
        else
            return "notfound";

    }

    @RequestMapping("/dimetti")
    public String dimetti(
            @RequestParam(name="id", required=true) Long id) {

        Optional<Patient> patient = repository.findById(id);
        if(patient.isPresent()){
            patient.get().setDischargeDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            repository.save(patient.get());
            return "redirect:/list";
        }
        else
            return "notfound";
    }

    @RequestMapping("/delete")
    public String delete(
            @RequestParam(name="id", required=true) Long id) {

        Optional<Patient> patient = repository.findById(id);
        Optional<Cartella> cartella = repositoryCartelle.findById(id);
        if(patient.isPresent()){
            repository.deleteById(patient.get().getId());
            repositoryCartelle.deleteById(cartella.get().getId());
            return "redirect:/list";
        }
        else
            return "notfound";
    }

    @RequestMapping("/print")
    public String stampa(
            @RequestParam(name="id", required=true) Long id,
            Model model) {
        Optional<Patient> patient = repository.findById(id);
        Optional<Cartella> cartella = repositoryCartelle.findById(id);
        if(patient.isPresent()) {
            model.addAttribute("patient", patient.get());
            model.addAttribute("cartella", cartella.get());
            return "stampa";
        }
        else
            return "notfound";
    }
}