package am.egs.ejb.model.pojo;

import lombok.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class Author {

    private Long id;

    @NotEmpty
    private String authorName;
}
