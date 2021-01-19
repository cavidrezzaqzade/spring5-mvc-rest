package guru.springfamework.api.v1.model.OnlineSigner.GetFiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputGetFiles {
    List<GettingFileFromEdoc> files = new ArrayList<>();
}
