package guru.springfamework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TestPdfTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String SrpoName;
    private String PbName;
    private Long ProtocolSerie;

    @Column(name = "protocol_datetime")
    private Timestamp ProtocolDateTime;

    private PersonType PersonType;

    private String PerSurname;
    private String PerName;

}
