package progettoIngegneriaBianchi;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date admissionDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dischargeDate;

    protected Patient() {}

    public Patient(String firstName, String lastName, Date admissionDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.admissionDate = admissionDate;
    }

    @Override
    public String toString() {
        return String.format(
                "Nome: %s, Cognome: %s",
                firstName, lastName);
    }

    public Long getId() {
        return id;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public Date getAdmissionDate(){
        return admissionDate;
    }

    public Date getDischargeDate(){
        return dischargeDate;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setAdmissionDate(Date admissionDate){
        this.admissionDate = admissionDate;
    }

    public void setDischargeDate(Date dischargeDate){
        this.dischargeDate = dischargeDate;
    }
}
