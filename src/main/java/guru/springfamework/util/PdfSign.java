package guru.springfamework.util;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import guru.springfamework.api.v1.model.OnlineSigner.ComingFullSign.ComingFullSignData;
import guru.springfamework.api.v1.model.OnlineSigner.FullSign.SendingFile;
import guru.springfamework.api.v1.model.OnlineSigner.FullSign.SendingInfo;
import guru.springfamework.api.v1.model.OnlineSigner.GetFiles.GettingFileFromEdoc;
import guru.springfamework.api.v1.model.OnlineSigner.GetFiles.GettingFilesFromEdocData;
import guru.springfamework.api.v1.model.OnlineSigner.GetFiles.SendingEdoc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Component
public class PdfSign {

    private final RestTemplate restTemplate;

    public PdfSign(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void Sign(List<SendingFile> listSendingFiles){

        SendingInfo sendingInfo = new SendingInfo();
        sendingInfo.setSignFormat("Edoc");
        sendingInfo.setTsaClientName("Default");
        sendingInfo.setSignCertificateSerialNumber("11E618D4956FB5EB000300020B61");

        sendingInfo.setFiles(listSendingFiles);

        HttpHeaders headersSign = new HttpHeaders();
        headersSign.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        HttpEntity<SendingInfo> entitySign = new HttpEntity<>(sendingInfo, headersSign);

        ComingFullSignData comingFullSignData =  restTemplate.postForObject("http://localhost:18230/api/v1/signer/fullsign", entitySign, ComingFullSignData.class);
        //headers.add("Content-Disposition", "inline; filename=" + comingFullSignData.getOutput().getEdocFile().getName() + ".pdf");

        SendingEdoc sendingEdoc = new SendingEdoc();
        sendingEdoc.setSignFormat("Edoc");
        sendingEdoc.setRawData(comingFullSignData.getOutput().getEdocFile().getRawData());

        HttpHeaders headersGetFiles = new HttpHeaders();
        headersGetFiles.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        HttpEntity<SendingEdoc> entityGetFiles = new HttpEntity<>(sendingEdoc, headersGetFiles);

        GettingFilesFromEdocData gettingFilesFromEdocData = restTemplate.postForObject("http://localhost:18230/api/v1/signer/getfiles", entityGetFiles, GettingFilesFromEdocData.class);

        for(GettingFileFromEdoc gettingFileFromEdoc : gettingFilesFromEdocData.getOutput().getFiles() ){
            try (FileOutputStream fos = new FileOutputStream("C:\\\\Users\\\\Cavid\\\\Desktop\\\\Test-Pdf-Signs\\\\" + gettingFileFromEdoc.getName()+".pdf")) {
                byte[] decoder = Base64.getDecoder().decode(gettingFileFromEdoc.getRawData());

                fos.write(decoder);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //@Scheduled(fixedRate = 120000)
    public void SignRun(){
        log.info("Sign Run Executed!!!!!!");
       // Sign(PdfGenerator.GeneratePdf());
    }
}
