package progettoIngegneriaBianchi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.*;
import java.util.Date;

@DataJpaTest
@RunWith(SpringRunner.class)
public class PatientTest {
    @Autowired
    private PatientRepository repository;

    private Patient patient;
    private Patient patient2;
    private Date currentDate = new Date();


    @Before
    public void setUp(){
        patient = new Patient();

        String firstName = "Michele";
        String lastName = "Bianchi";
        patient2 = new Patient(firstName, lastName, currentDate);

        repository.save(patient);
        repository.save(patient2);

        patient = repository.findByOrderById().get(0);
        patient2 = repository.findByOrderById().get(1);

    }

    @After
    public void clean(){
        repository.deleteAll();
    }

    @Test
    public void testNewPatient(){
        Patient patient = new Patient();
    }

    @Test
    public void testInitializedPatient(){
        String firstName = "Michele";
        String lastName = "Bianchi";
        Date currentDate = new Date();
        Patient patient = new Patient(firstName, lastName, currentDate);
    }

    @Test
    public void testGetter(){
        //Testing "empty" patient Constructor
        assertNotNull("Il costruttore senza parametri deve comunque assegnare un Id",patient.getId());
        assertNull("Il costruttore senza parametri deve creare un paziente con cognome a null",patient.getLastName());
        assertNull("Il costruttore senza parametri deve creare un paziente con data ricovero a null",patient.getAdmissionDate());
        assertNull("Il costruttore senza parametri deve creare un paziente con data dimissione a null",patient.getDischargeDate());

        //Testing initialized patient Constructor
        assertNotNull("Il costruttore deve comunque assegnare un Id",patient2.getId());
        assertEquals("Il nome deve essere quello specificato nel costruttore","Michele", patient2.getFirstName());
        assertEquals("Il cognome deve essere quello specificato nel costruttore","Bianchi", patient2.getLastName());
        assertEquals("La data di ricovero deve essere quella specificato nel costruttore",currentDate, patient2.getAdmissionDate());
        assertNull("La data di ricovero deve essere nulla alla creazione", patient2.getDischargeDate());
    }

    @Test
    public void testSetter(){
        assertNull("Il costruttore senza parametri deve creare un paziente con nome a null",patient.getFirstName());
        patient.setFirstName("Michele");
        assertEquals("Il nome deve essere quello specificato nel metodo","Michele", patient.getFirstName());

        assertNull("Il costruttore senza parametri deve creare un paziente con cognome a null",patient.getLastName());
        patient.setLastName("Bianchi");
        assertEquals("Il cognome deve essere quello specificato nel metodo","Bianchi", patient.getLastName());

        assertNull("Il costruttore senza parametri deve creare un paziente con data di ricovero a null",patient.getAdmissionDate());
        Date date = new Date();
        patient.setAdmissionDate(date);
        assertEquals("La data di ricovero deve essere quella specificata nel metodo",date, patient.getAdmissionDate());

        assertNull("Il costruttore senza parametri deve creare un paziente con data di dimissione a null",patient.getDischargeDate());
        patient.setDischargeDate(date);
        assertEquals("La data di dimissione deve essere quella specificata nel metodo",date, patient.getDischargeDate());
    }

    @Test
    public void testToString(){
        assertEquals("Formato toString sbagliato",patient.toString(),"Nome: "+patient.getFirstName()+", Cognome: "+patient.getFirstName());
    }


}
