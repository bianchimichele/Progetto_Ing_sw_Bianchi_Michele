package progettoIngegneriaBianchi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
public class CartellaTest {
    @Autowired
    private CartellaRepository repository;

    private Cartella cartella;

    @Before
    public void setUp(){
        Long id = (long)1;


        String drugs = "Tachipirina";
        String prescription = "Prendere 3 volte al giorno";

        cartella = new Cartella(id, drugs,prescription);

        repository.save(cartella);

        cartella = repository.findByOrderById().get(0);
    }

    @After
    public void clean(){
        repository.deleteAll();
    }

    @Test
    public void testNewCartella(){
        Cartella cartella = new Cartella((long) 1,"Tachipirina", "3 volte al giorno");
    }

    @Test
    public void testGetter(){
        Long id = (long)1;

        assertEquals("L'id deve essere quello specificato nel costruttore", id,cartella.getId());
        assertEquals("I farmaci devono essere quelli specificati nel costruttore","Tachipirina",cartella.getDrugs());
        assertEquals("La prescrizione deve essere quella specificata nel costruttore","Prendere 3 volte al giorno",cartella.getPrescription());
    }

    @Test
    public void testSetter(){
        cartella.setDrugs("Nimesulide");
        assertEquals("Il farmaco deve essere quello specificato dal metodo","Nimesulide", cartella.getDrugs());

        cartella.setPrescription("Prendere 5 volte al giorno");
        assertEquals("La prescrizione deve essere quella specificata dal metodo","Prendere 5 volte al giorno", cartella.getPrescription());
    }

    @Test
    public void testToString(){
        assertEquals("Formato toString sbagliato",cartella.toString(),"Farmaco: "+cartella.getDrugs()+", Prescrizione: "+cartella.getPrescription());
    }
}