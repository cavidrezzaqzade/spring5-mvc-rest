package guru.springfamework.api.v1.model.OnlineSigner.FullSign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendingInfo {
    String signFormat;
    String tsaClientName;
    String signCertificateSerialNumber;
    List<SendingFile> files;
}
