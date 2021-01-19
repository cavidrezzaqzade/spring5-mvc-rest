package guru.springfamework.api.v1.model.OnlineSigner.ComingFullSign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComingFullSignData {
    ComingOutput output;
    Object error;
    Boolean isSuccess;
}
