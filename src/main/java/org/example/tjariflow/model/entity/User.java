package org.example.tjariflow.model.entity;
import org.example.tjariflow.model.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public abstract class User extends BaseEntity{
    @Column(name="user_name" , nullable = false , unique = true)
    private String userName;
    @Column(name="password" , nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Roles role ;

}
