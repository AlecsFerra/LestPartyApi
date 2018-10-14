package it.alecsferra.letspartylogin.core.repository;

import it.alecsferra.letspartylogin.core.model.entity.Party;
import it.alecsferra.letspartylogin.core.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {

    List<Party> findAllByCreatorIdOrderByDate(String creatorId);

    List<Party> findAllByProvinceOrderByDate(String province);

}
