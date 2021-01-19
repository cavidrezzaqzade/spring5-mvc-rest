package guru.springfamework.api.v1.model.OnlineSigner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Output {
    List<Certificate> certificates;
}
