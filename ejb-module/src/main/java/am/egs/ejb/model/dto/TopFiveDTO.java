package am.egs.ejb.model.dto;

import am.egs.ejb.model.entity.BookEntity;
import am.egs.ejb.model.pojo.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TopFiveDTO {

    private Object object;

    private Long count;
}
