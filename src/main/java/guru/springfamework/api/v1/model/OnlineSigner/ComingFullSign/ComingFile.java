package guru.springfamework.api.v1.model.OnlineSigner.ComingFullSign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComingFile {
    String name;
    byte[] rawData;
}
