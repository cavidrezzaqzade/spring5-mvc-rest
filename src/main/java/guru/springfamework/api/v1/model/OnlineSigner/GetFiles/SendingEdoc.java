package guru.springfamework.api.v1.model.OnlineSigner.GetFiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendingEdoc {
    String signFormat;
    byte[] rawData;
}
