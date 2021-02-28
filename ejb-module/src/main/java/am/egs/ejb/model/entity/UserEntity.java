package am.egs.ejb.model.entity;

import am.egs.ejb.model.BaseEntity;
import am.egs.ejb.model.UserRole;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Builder
@Table(name = "user")
public class UserEntity extends BaseEntity implements Serializable {

    @Column(name = "first_name", nullable = false, length = 11)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", columnDefinition = "varchar(255) default 'USER'", insertable = false, nullable = false)
    private UserRole role;
}
