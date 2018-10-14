package it.alecsferra.letspartylogin.core.repository;

import it.alecsferra.letspartylogin.core.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);

}