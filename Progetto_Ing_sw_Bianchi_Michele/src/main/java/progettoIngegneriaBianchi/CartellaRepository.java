package progettoIngegneriaBianchi;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartellaRepository extends CrudRepository<Cartella, Long> {

        Cartella findById(long id);

        List<Cartella> findByOrderById();
}
