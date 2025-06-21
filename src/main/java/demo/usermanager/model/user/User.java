package demo.usermanager.model.user;

import demo.usermanager.model.role.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String username;

    @Column(unique = true)
    String email;
    String name;
    String surname;
    String taxCode;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    List<Role> roles;

}
