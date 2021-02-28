package am.egs.ejb.model.entity;

import am.egs.ejb.model.AdminRole;
import am.egs.ejb.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "admin")
public class AdminEntity extends BaseEntity implements Serializable {

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", columnDefinition = "varchar(255) default 'ADMIN'", insertable = false, nullable = false)
    private AdminRole role;
}
