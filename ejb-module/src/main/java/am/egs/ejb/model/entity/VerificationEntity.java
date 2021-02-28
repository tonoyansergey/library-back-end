package am.egs.ejb.model.entity;

import am.egs.ejb.model.BaseEntity;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Builder
@Table(name = "verification_table")
public class VerificationEntity extends BaseEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "v_code", nullable = false)
    private String vCode;
}
