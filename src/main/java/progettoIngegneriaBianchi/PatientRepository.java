package progettoIngegneriaBianchi;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {

        List<Patient> findByLastName(String last_name);

        List<Patient> findByOrderByIdDesc();

        List<Patient> findByOrderById();

        Patient findById(long id);
}
