package progettoIngegneriaBianchi;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Cartella {

    //@Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    private Long id;
    private String drugs;
    private String prescription;


    protected Cartella() {}

    public Cartella(Long id, String drugs, String prescription) {
        this.id = id;
        this.drugs = drugs;
        this.prescription = prescription;
    }

    @Override
    public String toString() {
        return String.format(
                "Farmaco: %s, Prescrizione: %s",
                drugs, prescription);
    }

    public Long getId() {
        return id;
    }

    public String getDrugs(){
        return drugs;
    }

    public String getPrescription(){
        return prescription;
    }

    public void setDrugs(String drugs){
        this.drugs = drugs;
    }

    public void setPrescription(String prescription){
        this.prescription = prescription;
    }

}
