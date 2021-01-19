package guru.springfamework.services;

import guru.springfamework.api.v1.TestPdf.TestPdfApiDelegate;
import guru.springfamework.api.v1.mapper.TestPdfMapper;
import guru.springfamework.api.v1.model.OnlineSigner.ComingFullSign.ComingFullSignData;
import guru.springfamework.api.v1.model.OnlineSigner.FullSign.SendingFile;
import guru.springfamework.api.v1.model.OnlineSigner.FullSign.SendingInfo;
import guru.springfamework.api.v1.model.OnlineSigner.GetFiles.GettingFilesFromEdocData;
import guru.springfamework.api.v1.model.OnlineSigner.GetFiles.SendingEdoc;
import guru.springfamework.domain.SignData;
import guru.springfamework.repositories.EdocRepository;
import guru.springfamework.repositories.TestPdfRepository;
import guru.springfamework.util.PdfGenerator;
import guru.springfamework.util.PdfSign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Service
@Slf4j
public class TestPdfService implements TestPdfApiDelegate {

    //private final TestPdfRepository testPdfRepository;
    //private final TestPdfMapper testPdfMapper;

    private final EdocRepository edocRepository;
    private final RestTemplate restTemplate;

    public TestPdfService(TestPdfRepository testPdfRepository, TestPdfMapper testPdfMapper, RestTemplate restTemplate, EdocRepository edocRepository) {
        //this.testPdfRepository = testPdfRepository;
        //this.testPdfMapper = testPdfMapper;
        this.edocRepository = edocRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<?> /*ComingFullSignData*/ /*String*/ testPdfReport() {
        /*long time1 = System.nanoTime();
        log.info(time1);*/
        SendingInfo sendingInfo = new SendingInfo();
        sendingInfo.setSignFormat("Edoc");
        sendingInfo.setTsaClientName("Default");
        sendingInfo.setSignCertificateSerialNumber("11E618D4956FB5EB000300020B61");

        SendingFile sendingFile = new SendingFile();
        sendingFile.setName("test_pdf_sign");
        //sendingFile.setRawData(PdfGenerator.GeneratePdf());

        List<SendingFile> listSendingFiles = new ArrayList<>();
        listSendingFiles.add(sendingFile);

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


        ByteArrayInputStream bis = new ByteArrayInputStream(Base64.getDecoder().decode(gettingFilesFromEdocData.getOutput().getFiles().get(0).getRawData()));
       // ByteArrayInputStream bis = new ByteArrayInputStream(gettingFilesFromEdocData.getOutput().getFiles().get(0).getRawData());


        return ResponseEntity
                .ok()
                .headers(headersGetFiles)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @Override
    public ResponseEntity<?> testFullSign() {

        SendingInfo sendingInfo = new SendingInfo();
        sendingInfo.setSignFormat("Edoc");
        sendingInfo.setTsaClientName("Default");
        sendingInfo.setSignCertificateSerialNumber("11E618D4956FB5EB000300020B61");

        SendingFile sendingFile = new SendingFile();
        sendingFile.setName("test_pdf_sign");
        //sendingFile.setRawData(PdfGenerator.GeneratePdf());

        List<SendingFile> listSendingFiles = new ArrayList<>();
        listSendingFiles.add(sendingFile);

        sendingInfo.setFiles(listSendingFiles);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        HttpEntity<SendingInfo> entity = new HttpEntity<>(sendingInfo, headers);


        //return restTemplate.exchange("http://localhost:18230/version", HttpMethod.GET, entity, String.class).getBody() + " ve " + restTemplate.exchange("http://localhost:18230/hostversion", HttpMethod.GET, entity, String.class).getBody();
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(restTemplate.postForEntity("http://localhost:18230/api/v1/signer/fullsign", entity, ComingFullSignData.class).getBody());
        //return restTemplate.getForEntity("http://localhost:18230/api/v1/signer/readcertificatesfromstore", CertificateDetails.class);
    }

    @Override
    public String testSignAndGetFileToDesktop() {

        PdfSign pdfSign = new PdfSign(restTemplate);
        List<SignData> datas = edocRepository.findAll();
        pdfSign.Sign(PdfGenerator.GeneratePdf(datas));

        return "Servisin Sign methodu ise salindi ve desktopda edoc file yaradildi!!!";
    }

    @Override
    public List<SignData> testOracleSelect() {
        return edocRepository.findAll();
    }
}
