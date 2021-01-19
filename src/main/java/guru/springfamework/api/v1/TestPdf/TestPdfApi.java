package guru.springfamework.api.v1.TestPdf;


import guru.springfamework.domain.SignData;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public interface TestPdfApi {

    default TestPdfApiDelegate getDelegate() {
        return new TestPdfApiDelegate() {
        };
    }

    @GetMapping(value = "testPdfReport",
            produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseStatus(HttpStatus.OK)
    default ResponseEntity<?>  testPdfReport() {
        return getDelegate().testPdfReport();
    }

    @GetMapping(value = "testSignAndGetFileToDesktop")
    @ResponseStatus(HttpStatus.OK)
    default String  testSignAndGetFileToDesktop() {
        return getDelegate().testSignAndGetFileToDesktop();
    }

    @PostMapping(value = "testFullSign", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    default ResponseEntity<?> testFullSign() {
        return getDelegate().testFullSign();
    }

    @GetMapping(value = "testOracleSelect")
    @ResponseStatus(HttpStatus.OK)
    default List<SignData> testOracleSelect() {
        return getDelegate().testOracleSelect();
    }

}
