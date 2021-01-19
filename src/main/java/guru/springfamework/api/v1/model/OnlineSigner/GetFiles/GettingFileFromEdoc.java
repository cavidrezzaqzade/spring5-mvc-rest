package guru.springfamework.api.v1.model.OnlineSigner.GetFiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GettingFileFromEdoc {
    String name;
    byte[] rawData;
}
