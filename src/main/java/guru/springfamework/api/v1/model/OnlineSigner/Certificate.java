package guru.springfamework.api.v1.model.OnlineSigner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Certificate {
    String serialNumber;
    String subject;
}
