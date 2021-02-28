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
@Table(name = "genre")
public class GenreEntity extends BaseEntity implements Serializable {

    @Column(name = "genre_name", nullable = false)
    private String genreName;
}
