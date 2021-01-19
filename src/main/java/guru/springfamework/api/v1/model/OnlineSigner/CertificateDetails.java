package guru.springfamework.api.v1.model.OnlineSigner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateDetails {
    Output output;
    String error;
    boolean success;
}
