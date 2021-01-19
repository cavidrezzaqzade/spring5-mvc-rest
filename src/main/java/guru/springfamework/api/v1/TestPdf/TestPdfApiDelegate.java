package guru.springfamework.api.v1.TestPdf;

import guru.springfamework.domain.SignData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;


public interface TestPdfApiDelegate {

    default ResponseEntity<?> /*ComingFullSignData*/ /*String*/ testPdfReport(){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        //return  new String();
    }

    default ResponseEntity<?> testFullSign(){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    default String testSignAndGetFileToDesktop(){
        return "api delegate-de default method caqirildi cunki servisde override olunmayib!!!";
    }

    default List<SignData> testOracleSelect(){
        return new ArrayList<>();
    }

}
