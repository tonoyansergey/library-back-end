package am.egs.ejb.model.pojo;

import lombok.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class Book {

    private Long id;

    @NotEmpty
    private String title;

    @NotNull
    private Author author;

    @NotNull
    private Genre genre;

    @NotNull
    private Long quantity;
}
