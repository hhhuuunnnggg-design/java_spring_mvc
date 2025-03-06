package vn.hoidanit.laptopshop.entity.test;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vn.hoidanit.laptopshop.entity.User;

@Entity
@Getter
@Setter
@Table(name = "user_role")
public class Role_User implements Serializable { // Thêm Serializable
    private static final long serialVersionUID = 1L; // Thêm UID cho Serializable

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Một Role_User thuộc về một User

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Rolee rolee;

}
