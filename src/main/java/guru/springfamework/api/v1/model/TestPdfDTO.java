package guru.springfamework.api.v1.model;

import guru.springfamework.domain.PersonType;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TestPdfDTO {

    private Long Id;

    private String SrpoName;

    private String PbName;

    private Long ProtocolSerie;

    private Timestamp ProtocolDateTime;

    private PersonType PersonType;

    private String PerSurname;

    private String PerName;

}
