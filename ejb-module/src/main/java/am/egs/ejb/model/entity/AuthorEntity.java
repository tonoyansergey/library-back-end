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
@Table(name = "author")
public class AuthorEntity extends BaseEntity implements Serializable {

    @Column(name = "author_name", nullable = false)
    private String authorName;
}
