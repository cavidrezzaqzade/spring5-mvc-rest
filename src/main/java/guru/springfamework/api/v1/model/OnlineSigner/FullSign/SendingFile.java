package guru.springfamework.api.v1.model.OnlineSigner.FullSign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendingFile {
    String name;
    byte[] rawData;
}
