package vn.hoidanit.laptopshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User newUser);

    List<User> findByEmailAndAdress(String email, String adress);

    List<User> findOneByEmail(String email);

    User findFirstByEmail(String email);

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> deleteById(long id);

    boolean existsByEmail(String email);

    List<User> findByEmail(String email);

    // Sửa method findByEmailWithRoles
    @EntityGraph(attributePaths = { "roleUsers" }) // Sửa "user_role" thành "roleUsers"
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmailWithRoles(String email);

}
