package vn.hoidanit.laptopshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User newUser);

    List<User> findByEmailAndAdress(String email, String adress);

    List<User> findByEmail(String email);

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> deleteById(long id);
}
