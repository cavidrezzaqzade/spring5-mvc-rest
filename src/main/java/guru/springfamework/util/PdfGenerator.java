package guru.springfamework.util;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import guru.springfamework.api.v1.model.OnlineSigner.FullSign.SendingFile;
import guru.springfamework.domain.SignData;
import guru.springfamework.domain.TestPdfTable;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import javafx.scene.CacheHint;
import javafx.scene.control.Cell;
import lombok.extern.slf4j.Slf4j;
import org.intellij.lang.annotations.JdkConstants;


@Slf4j
public class PdfGenerator {


    public static /*ByteArrayInputStream*/ /*byte[]*/ List<SendingFile> GeneratePdf(List<SignData> datas)  {

        Rectangle pageSize = new Rectangle(PageSize.A4);
        pageSize.setBackgroundColor(new BaseColor(255, 255, 255));

        List<SendingFile> listSendingFiles = new ArrayList<>();

        try {

            BaseFont bf = BaseFont.createFont("C:\\Users\\Cavid\\IdeaProjects\\spring5-mvc-rest-vendor-api\\src\\main\\resources\\static\\ARIALUNI.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fontAzWhite = new Font(bf, 12, -1, new BaseColor(255,255,255));
            Font fontAzBlack12 = new Font(bf, 12, -1, new BaseColor(0,0,0));
            Font fontAzBlack = new Font(bf, 9, -1, new BaseColor(0,0,0));

            for(SignData data : datas){

                Document document = new Document();
                document.setPageSize(pageSize);
                document.setMargins(5, 5, 10, 1);

                PdfPTable tableProt = new PdfPTable(10);
                tableProt.setWidthPercentage(100);
                tableProt.setSpacingBefore(10f);
                tableProt.setSpacingAfter(10f);

                PdfPTable tableDec = new PdfPTable(10);
                tableDec.setWidthPercentage(100);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                PdfWriter.getInstance(document, byteArrayOutputStream);
                document.open();

                //Protokol
                Chunk chunkProt = new Chunk("İnzibati xəta haqqında PROTOKOL " , fontAzWhite);
                Chunk chunkProtSerie = new Chunk(data.getProtocol_no_serie() + " № " + data.getProtocol_no(), fontAzBlack12);
                Chunk chunkProtTime = new Chunk(data.getProtocol_date_time().toString(), fontAzWhite);
                Chunk glueProt = new Chunk(new VerticalPositionMark());

                Phrase headerProtPhrase = new Phrase();
                headerProtPhrase.add(chunkProt);
                headerProtPhrase.add(chunkProtSerie);
                headerProtPhrase.add(glueProt);
                headerProtPhrase.add(chunkProtTime);

                tableProt.addCell(createCell(new Paragraph(headerProtPhrase), new BaseColor(191,191,191), 1.5f, 10, 5f, 0));

                //Protokolun tertib edildiyi yer
                Chunk chunkProtCrLocLbl = new Chunk("Protokolun tərtib edildiyi yer", fontAzBlack);
                Chunk chunkProtCrLocText = new Chunk(data.getSrpo_name() + data.getPb_name(), fontAzBlack);

                tableProt.addCell(createCell(new Paragraph(new Phrase(chunkProtCrLocLbl)), new BaseColor(230, 230, 230), 1f, 3, 2f, 1));
                tableProt.addCell(createCell(new Paragraph(new Phrase(chunkProtCrLocText)), BaseColor.WHITE, 1f, 7, 3f, 0));

                //Protokolu tertib eden
                Chunk chunkConstructorInfoLbl = new Chunk("Protokolu tərtib edən şəxsin vəzifəsi, soyadı, adı, atasının adı", fontAzBlack);
                Chunk chunkConstructorInfoText = new Chunk(data.getConstructor_info(), fontAzBlack);

                tableProt.addCell(createCell(new Paragraph(new Phrase(chunkConstructorInfoLbl)), new BaseColor(230, 230, 230), 1f, 3, 2f, 1));
                tableProt.addCell(createCell(new Paragraph(new Phrase(chunkConstructorInfoText)), BaseColor.WHITE, 1f, 7, 3f, 0));

                //baresinde inzibati xeta haqqinda is aparilan sexs
                Chunk chunkAboutPerLbl = new Chunk("Barəsində inzibati xəta haqqında iş üzrə icraat aparılan fiziki şəxsin soyadı, adı, atasının adı, vəzifəli şəxs kimi məsuliyyətə cəlb edildikdə vəzifəsi, vətəndaşlığı, doğulduğu tarix, şəxsiyyətini təsdiq edən sənədin seriyası və nömrəsi, hüquqi şəxsin adı, təşkilati-hüquqi forması, hüquqi ünvanı və vergi ödəyicisinin eyniləşdirmə nömrəsi", new Font(bf, 8, -1, BaseColor.BLACK));
                Chunk chunkAboutPerText = new Chunk(" ");
                chunkAboutPerText.setFont(fontAzBlack);
                if(data.getPerson_type_id() == 60296){
                    chunkAboutPerText.append("Fiziki " + data.getPer_surname() + " " + data.getPer_name() + " " + data.getPer_patronymic() + ", " + data.getPer_citizenship() + ", " + data.getPer_birth_date() + ", " + data.getIdcard_serie() + " " + data.getIdcard_no());
                }
                if(data.getPerson_type_id() == 60298){
                    chunkAboutPerText.append("Vezifeli " + data.getPer_surname() + " " + data.getPer_name() + " " + data.getPer_patronymic() + ", " + data.getPer_citizenship() + ", " + data.getPer_birth_date() + ", " + data.getIdcard_serie() + " " + data.getIdcard_no());
                }
                if(data.getPerson_type_id() == 60297){
                    chunkAboutPerText.append("Huquqi " + data.getPer_surname() + " " + data.getPer_name() + " " + data.getPer_patronymic() + ", " + data.getPer_citizenship() + ", " + data.getPer_birth_date() + ", " + data.getIdcard_serie() + " " + data.getIdcard_no());
                }


                tableProt.addCell(createCell(new Paragraph(new Phrase(chunkAboutPerLbl)), new BaseColor(230, 230, 230), 1f, 3, 2f, 1));
                tableProt.addCell(createCell(new Paragraph(new Phrase(chunkAboutPerText)), BaseColor.WHITE, 1f, 7, 3f, 0));

                //baresinde mueyyen edildi
                Chunk chunkEmpty = new Chunk();
                Chunk chunkAboutDefine = new Chunk("barəsində müəyyən edildi" , fontAzBlack);
                tableProt.addCell(createCell(new Paragraph(new Phrase(chunkEmpty)), BaseColor.WHITE, 1f, 3, 2f, 0));
                tableProt.addCell(createCell(new Paragraph(new Phrase(chunkAboutDefine)), new BaseColor(230,230,230), 1f, 7, 3f, 1));

                //Inzibati xetanin qisa mecmunu
                Chunk chunkOffenceLbl =  new Chunk("İnzibati xətanın törədildiyi yer, vaxt və bu xətanın mahiyyəti", fontAzBlack);
                Chunk chunkOffenceText = new Chunk(data.getExecute_region() + "," + data.getExecute_place() + "," + data.getExecute_date() + "," + data.getExecute_time() + "," + data.getShort_content(), fontAzBlack);
                tableProt.addCell(createCell(new Paragraph(new Phrase(chunkOffenceLbl)), new BaseColor(230, 230, 230), 1f, 3, 3f, 1));
                tableProt.addCell(createCell(new Paragraph(new Phrase(chunkOffenceText)), BaseColor.WHITE, 1f, 10, 3f, 0));

                //Neticede
                Chunk chunkLaw = new Chunk( " " + data.getIxm_codes() + " ", fontAzBlack);
                chunkLaw.setUnderline(0.5f, -2f);
                Chunk chunkPer = new Chunk("");
                chunkPer.setFont(fontAzBlack);
                if(data.getPerson_type_id() == 60296 || data.getPerson_type_id() == 60298){
                    chunkPer.append(data.getPer_surname() + " " + data.getPer_name() + " " + data.getPer_patronymic());
                }
                if(data.getPerson_type_id() == 60297){
                    chunkPer.append(data.getJper_name());
                }
                chunkPer.setUnderline(0.5f, -2f);

                Phrase phraseResult = new Phrase();
                phraseResult.setFont(fontAzBlack);
                phraseResult.add(chunkPer);
                phraseResult.add(" tərəfindən Azərbaycan Respublikasının İnzibati Xətalar Məcəlləsinin ");
                phraseResult.add(chunkLaw);
                phraseResult.add(" maddəsində nəzərdə tutulmuş inzibati xəta törədilmişdir.");

                Chunk chunkResultEmpty = new Chunk();
                Chunk chunkResultLbl = new Chunk("nəticədə" , fontAzBlack);
                Chunk chunkResultText = new Chunk("İnzibati xətanı törədən fiziki şəxsin adı, atasının adı və soyadı, hüquqi şəxsin adı", fontAzBlack);

                tableProt.addCell(createCell(new Paragraph(new Phrase(chunkResultEmpty)), BaseColor.WHITE, 1f, 3, 2f, 0));
                tableProt.addCell(createCell(new Paragraph(new Phrase(chunkResultLbl)), new BaseColor(230,230,230), 1f, 10, 3f, 1));

                tableProt.addCell(createCell(new Paragraph(new Phrase(chunkResultText)), new BaseColor(230, 230, 230), 1f, 3, 3f, 1));
                tableProt.addCell(createCell(new Paragraph(phraseResult), BaseColor.WHITE, 1f, 7, 3f, 0));

                document.add(tableProt);


                //Qerar
                Chunk chunkDec = new Chunk("İnzibati tənbeh vermə haqqında QƏRAR " , fontAzWhite);
                Chunk chunkDecSerie = new Chunk(data.getDec_serie() + " № " + data.getDec_no(), fontAzBlack12);
                Chunk chunkDecTime = new Chunk(String.valueOf(data.getDecision_date()), fontAzWhite);
                Chunk glueDec = new Chunk(new VerticalPositionMark());

                Phrase headerDecPhrase = new Phrase();
                headerDecPhrase.add(chunkDec);
                headerDecPhrase.add(chunkDecSerie);
                headerDecPhrase.add(glueDec);
                headerDecPhrase.add(chunkDecTime);

                tableDec.addCell(createCell(new Paragraph(headerDecPhrase), new BaseColor(191,191,191), 1.5f, 10, 5f, 0));

                //Qerarin qebul edildiyi yer
                Chunk chunkDecOrgLbl = new Chunk("Qərarın qəbul edildiyi yer", fontAzBlack);
                Chunk chunkDecOrgText = new Chunk(data.getDecision_maker_str(), fontAzBlack);

                tableDec.addCell(createCell(new Paragraph(new Phrase(chunkDecOrgLbl)), new BaseColor(230, 230, 230), 1f, 3, 2f, 1));
                tableDec.addCell(createCell(new Paragraph(new Phrase(chunkDecOrgText)), BaseColor.WHITE, 1f, 7, 3f, 0));

                //Qerari qebul eden shexs
                Chunk chunkDecMakerInfoLbl = new Chunk("Qərarı qəbul edən vəzifəli şəxsin vəzifəsi, soyadı, adı, atasının adı, kollegial orqanın adı və tərkibi", fontAzBlack);
                Chunk chunkDecMakerInfoText = new Chunk(data.getDecision_maker_info(), fontAzBlack);

                tableDec.addCell(createCell(new Paragraph(new Phrase(chunkDecMakerInfoLbl)), new BaseColor(230, 230, 230), 1f, 3, 2f, 1));
                tableDec.addCell(createCell(new Paragraph(new Phrase(chunkDecMakerInfoText)), BaseColor.WHITE, 1f, 7, 3f, 0));

                //baresinde mueyyen edildi
                Chunk chunkDecPerLbl = new Chunk("Müəyyən olunduğu halda barəsində inzibati xəta haqqında iş üzrə icraat aparılan fiziki şəxsin soyadı, adı, atasının adı, vəzifəli şəxs kimi məsuliyyətə cəlb edildikdə vəzifəsi, vətəndaşlığı, doğulduğu tarix, şəxsiyyətini təsdiq edən sənədin seriyası və nömrəsi", new Font(bf, 8, -1, BaseColor.BLACK));
                //Chunk chunkDecPerText = new Chunk("Məmmədhəsənov Məmmədəmin Ənvər, Azərbaycan Respublikası vətəndaşı, 12/18/1957, AZE, 07636006" , fontAzBlack);
                tableDec.addCell(createCell(new Paragraph(new Phrase(chunkDecPerLbl)), new BaseColor(230,230,230), 1f, 3, 2f, 1));
                tableDec.addCell(createCell(new Paragraph(new Phrase(/*chunkDecPerText*/chunkAboutPerText)), BaseColor.WHITE, 1f, 7, 3f, 0));

                //baresinde tertib edilmis
                Chunk chunkDecEmpty = new Chunk();
                Chunk chunkDecAboutDefine = new Chunk("barəsində tərtib edilmiş" , fontAzBlack);
                tableDec.addCell(createCell(new Paragraph(new Phrase(chunkDecEmpty)), BaseColor.WHITE, 1f, 3, 2f, 0));
                tableDec.addCell(createCell(new Paragraph(new Phrase(chunkDecAboutDefine)), new BaseColor(230,230,230), 1f, 7, 3f, 1));

                //protokol date and serie
           /* Chunk chunkDecProtInfoDate = new Chunk(" 28.08.2020 17:13 " , fontAzBlack);
            chunkDecProtInfoDate.setUnderline(0.5f, -2f);
            Chunk chunkDecProtInfoSerie = new Chunk(" MN № 0095847 ", fontAzBlack);
            chunkDecProtInfoSerie.setUnderline(0.5f, -2f);*/

                Phrase phraseDecProtInfo = new Phrase();
                phraseDecProtInfo.setFont(fontAzBlack);
                chunkProtTime.setFont(fontAzBlack);
                phraseDecProtInfo.add(/*chunkDecProtInfoDate*/chunkProtTime);
                phraseDecProtInfo.add(" tarixli ");
                chunkProtSerie.setFont(fontAzBlack);
                phraseDecProtInfo.add(/*chunkDecProtInfoSerie*/chunkProtSerie);
                phraseDecProtInfo.add(" nömrəli protokola/prokurorun qərarına (lazım olmayanın üstündən xətt çəkilir) və inzibati xəta haqqında işin digər materiallarına baxılaraq");

                tableDec.addCell(createCell(new Paragraph(new Phrase("Protokolun seriya, nömrə və tarixi", fontAzBlack)), new BaseColor(230,230,230), 1f, 3, 2f, 1));
                tableDec.addCell(createCell(new Paragraph(new Phrase(phraseDecProtInfo)), BaseColor.WHITE, 1f, 7, 3f, 0));

                //mueyyen edildi
                Chunk chunkDecMake = new Chunk("müəyyən edildi" , fontAzBlack);
                tableDec.addCell(createCell(new Paragraph(new Phrase(chunkDecEmpty)), BaseColor.WHITE, 1f, 3, 2f, 0));
                tableDec.addCell(createCell(new Paragraph(new Phrase(chunkDecMake)), new BaseColor(230,230,230), 1f, 7, 3f, 1));

                //ise baxilarken mueyyen edilmis hallar
                Chunk chunkDecIdentifiedCasesLbl = new Chunk("İşə baxılarkən müəyyən edilmiş hallar", new Font(bf, 9    , -1, BaseColor.BLACK));
                Chunk chunkDecIdentifiedCasesText = new Chunk("Müəyyən edilmiş məlumat yoxdur!" , fontAzBlack);
                tableDec.addCell(createCell(new Paragraph(new Phrase(chunkDecIdentifiedCasesLbl)), new BaseColor(230,230,230), 1f, 3, 2f, 1));
                tableDec.addCell(createCell(new Paragraph(new Phrase(chunkDecIdentifiedCasesText)), BaseColor.WHITE, 1f, 7, 3f, 0));

                //neticede header
                Chunk chunkDecResult = new Chunk("nəticədə" , fontAzBlack);
                tableDec.addCell(createCell(new Paragraph(new Phrase(chunkDecEmpty)), BaseColor.WHITE, 1f, 3, 2f, 0));
                tableDec.addCell(createCell(new Paragraph(new Phrase(chunkDecResult)), new BaseColor(230,230,230), 1f, 7, 3f, 1));

                //mueyyen olunmus netice
           /* Chunk chunkDecLaw = new Chunk(" 211.2. ", fontAzBlack);
            chunkDecLaw.setUnderline(0.5f, -2f);*/
                // Chunk chunkDecPer = new Chunk(" Məmmədhəsənov Məmmədəmin Ənvər ", fontAzBlack);
                // chunkDecPer.setUnderline(0.5f, -2f);

                Phrase phraseDecResult = new Phrase();
                phraseDecResult.setFont(fontAzBlack);
                phraseDecResult.add(/*chunkDecPer*/chunkPer);
                phraseDecResult.add(" tərəfindən Azərbaycan Respublikasının İnzibati Xətalar Məcəlləsinin ");
                phraseDecResult.add(/*chunkDecLaw*/chunkLaw);
                phraseDecResult.add(" maddəsində nəzərdə tutulmuş inzibati xəta törədilmişdir.");

                Chunk chunkDecResultText = new Chunk("İnzibati xətanı törədən fiziki şəxsin soyadı, adı və atasının adı" , fontAzBlack);

                tableDec.addCell(createCell(new Paragraph(new Phrase(chunkDecResultText)), new BaseColor(230, 230, 230), 1f, 3, 3f, 1));
                tableDec.addCell(createCell(new Paragraph(phraseDecResult), BaseColor.WHITE, 1f, 7, 3f, 0));

                //hansi qerar alinmisdir
                Chunk chunkTakenDecLbl = new Chunk("Hansı qərarın qəbul edildiyi göstərilməlidir",fontAzBlack);
                //Chunk chunkTakenDecLaw = new Chunk(" 211.2. ", fontAzBlack);
                //chunkTakenDecLaw.setUnderline(0.5f, -2f);
                Chunk chunkTakenDecKind = new Chunk(data.getDec_name(), fontAzBlack);
                chunkTakenDecKind.setUnderline(0.5f, -2f);


                Phrase phraseTakenDec = new Phrase();
                phraseTakenDec.setFont(fontAzBlack);
                phraseTakenDec.add("Azərbaycan Respublikasının İnzibati Xətalar Məcəlləsinin ");
                phraseTakenDec.add(/*chunkTakenDecLaw*/chunkLaw);
                phraseTakenDec.add(" maddəsinə əsasən ");
                phraseTakenDec.add(chunkTakenDecKind);


                tableDec.addCell(createCell(new Paragraph(new Phrase(chunkTakenDecLbl)), new BaseColor(230, 230, 230), 1f, 3, 3f, 1));
                tableDec.addCell(createCell(new Paragraph(phraseTakenDec), BaseColor.WHITE, 1f, 7, 3f, 0));

                document.add(tableDec);
                document.close();

                byte[] decoder = Base64.getEncoder().encode(byteArrayOutputStream.toByteArray());

                SendingFile sendingFile = new SendingFile();
                sendingFile.setName("pdf_sign_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
                sendingFile.setRawData(decoder);

                listSendingFiles.add(sendingFile);
            }
        }catch(DocumentException|IOException e) {
            log.info(e.toString());
           // document.close();
        }
        finally{
            //document.close();
        }

        return listSendingFiles;
        //return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
    public static PdfPCell createCell(Paragraph paragraph, BaseColor baseColor, float borderWidth, int colSpan, float padding, int alignment){

        PdfPCell pdfPCell = new PdfPCell(new Paragraph(paragraph));
        paragraph.setLeading(10);
        paragraph.setAlignment(alignment);
        pdfPCell.setColspan(colSpan);
        pdfPCell.setBackgroundColor(baseColor);
        pdfPCell.setBorderColor(new BaseColor(245,245,245));
        //pdfPCell.setBorderColorLeft(cellColorLeft);
        //pdfPCell.setBorderWidthBottom(2f);
        pdfPCell.setBorderWidth(borderWidth);
        pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        //pdfPCell.setBorder(Rectangle.BOX);
        pdfPCell.setPadding(padding);
        //pdfPCell.setPaddingLeft(4f);
        pdfPCell.setUseAscender(true);
        pdfPCell.addElement(paragraph);

        return pdfPCell;
    }
    private static PdfPCell getCell(int cm) {
        PdfPCell cell = new PdfPCell();
        cell.setColspan(cm);
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        Paragraph p = new Paragraph(
                String.format("%smm", 10 * cm),
                new Font(Font.FontFamily.HELVETICA, 8));
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        return cell;
    }

    /* public static ByteArrayInputStream TestPdfExReport(List<TestPdfTable> testPdfRows)  {
        Rectangle pageSize = new Rectangle(PageSize.A4);
        pageSize.setBackgroundColor(new BaseColor(201, 255, 254));
        Document document = new Document(pageSize);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            PdfReader reader = new PdfReader("C:\\\\Users\\\\Cavid\\\\Desktop\\\\test.pdf");

            PdfStamper pdfStamper = new PdfStamper(reader, byteArrayOutputStream);
            pdfStamper.close();

        }catch(DocumentException | IOException e) {
            //log.info(e.toString());
        }

        //byte[] encoded = Base64.getEncoder().encode(byteArrayOutputStream.toByteArray());

        //byte[]  pdfBytes = byteArrayOutputStream.toByteArray();

        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }*/
    /*    public static void TestPdfBase64(){
        File file = new File("C:\\\\Users\\\\Cavid\\\\Desktop\\\\testBase64.pdf");
        try (FileOutputStream fos = new FileOutputStream(file); ) {

            String b64 = "JVBERi0xLjQKJeLjz9MKNCAwIG9iago8PC9GaWx0ZXIvRmxhdGVEZWNvZGUvTGVuZ3RoIDc0Mj4+c3RyZWFtCniclVXBbtswDL37K3TMDtEkipLlHbeuWzagWBD3slu6ukWHtGmdDsP+fpRlJ7FNOQ0SIA4e+ahHPtEv2ccyM0545UR5m4FGmYu59uHP+0stNIjyLlMifOr7bFZWu9cft3eiXN9sqnflb4Lus3lMUyFJHbKhzZ6JGNjHj/4jhqfPZbbMXjIlcwsFiuEvVfdeGpETqQV6QDpRld1NJWhU0p2XUYAszsoAtBLOyyhyac/KMFhIf1YGKpD6vAyLEscZYSQgvtGcvmRa/GVnsGoQttktxHU1Qmz7WojrU4TYhkSIVd5CnMQVNcV2wpxTEbNwjEVpKbARlwCjvBTYCEyAUWICjCJTYCMzAUaZyk/ITIBRJg+2MhNglMmDrUwebGUmwChzCC7DRtPNwqL1pbxE8pChlfNIK8l0K2lxEXYSrZzjYBKBmgle1c/bq3VIGIaCC6Gzx0p8qjYbhrKwUiFD+ePmav1YHVMCHVXZPSXPB+il8xxfvX3d/tr2CC3VPpxxs6rqh35Fp6QJFwwnKhqlZPHGikYZ6Q4SNhfr16p86EfQqPS+ZLJvxmpZsH2r6t32qexx0vhDbFv133OKFBXKgpsvka7+1E/HpKidhAPpYFaocwmnOoc2l57tXFXT8Ht8DqU9VEu6SdN1oPvgVWTcv2A1E2udpKvhvBvE7sjMfSvnMqd7lEctTyRVMHygjTQc3/NN38UqGP4km7PBx4wSJtioIpjGFX4QDArUvG+vGBkPOdMwB+jjKJ3Zn06rD+Hbi6C5BwYHTQSJYc5D98ZxY7hc/Fx8XzBO0CoYzxUw7F3j5p4TSAE5q+We7ciWA6tA5HK+tQrTMHRGguZmNa7ndHDeqXl1zoPRCNLOM/hG5wEcajN8nfNGfLzzTrG1zhsrYYI7P+GwkUnnGZx2Xnu6CedpNeE8euWRM8et+Hq9vF722HJ6Ift9vQlT4tDFaVPGnk2YUnf7i+llZ8rxGJOm5Eb5H5xhp0cKZW5kc3RyZWFtCmVuZG9iago2IDAgb2JqCjw8L0NvbnRlbnRzIDQgMCBSL1R5cGUvUGFnZS9SZXNvdXJjZXM8PC9Qcm9jU2V0IFsvUERGIC9UZXh0IC9JbWFnZUIgL0ltYWdlQyAvSW1hZ2VJXS9Gb250PDwvRjEgMSAwIFIvRjIgMiAwIFIvRjMgMyAwIFI+Pj4+L1BhcmVudCA1IDAgUi9NZWRpYUJveFswIDAgNTk1IDg0Ml0+PgplbmRvYmoKMSAwIG9iago8PC9TdWJ0eXBlL1R5cGUxL1R5cGUvRm9udC9CYXNlRm9udC9Db3VyaWVyL0VuY29kaW5nL1dpbkFuc2lFbmNvZGluZz4+CmVuZG9iagoyIDAgb2JqCjw8L1N1YnR5cGUvVHlwZTEvVHlwZS9Gb250L0Jhc2VGb250L0hlbHZldGljYS9FbmNvZGluZy9XaW5BbnNpRW5jb2Rpbmc+PgplbmRvYmoKMyAwIG9iago8PC9TdWJ0eXBlL1R5cGUxL1R5cGUvRm9udC9CYXNlRm9udC9IZWx2ZXRpY2EtQm9sZC9FbmNvZGluZy9XaW5BbnNpRW5jb2Rpbmc+PgplbmRvYmoKNSAwIG9iago8PC9LaWRzWzYgMCBSXS9UeXBlL1BhZ2VzL0NvdW50IDEvSVRYVCg1LjAuNik+PgplbmRvYmoKNyAwIG9iago8PC9UeXBlL0NhdGFsb2cvUGFnZXMgNSAwIFI+PgplbmRvYmoKOCAwIG9iago8PC9Nb2REYXRlKEQ6MjAyMDEyMjIyMzMyMjcrMDQnMDAnKS9DcmVhdGlvbkRhdGUoRDoyMDIwMTIyMjIzMzIyNyswNCcwMCcpL1Byb2R1Y2VyKGlUZXh0IDUuMC42IFwoY1wpIDFUM1hUIEJWQkEpPj4KZW5kb2JqCnhyZWYKMCA5CjAwMDAwMDAwMDAgNjU1MzUgZiAKMDAwMDAwMDk5OSAwMDAwMCBuIAowMDAwMDAxMDg1IDAwMDAwIG4gCjAwMDAwMDExNzMgMDAwMDAgbiAKMDAwMDAwMDAxNSAwMDAwMCBuIAowMDAwMDAxMjY2IDAwMDAwIG4gCjAwMDAwMDA4MjQgMDAwMDAgbiAKMDAwMDAwMTMyOSAwMDAwMCBuIAowMDAwMDAxMzc0IDAwMDAwIG4gCnRyYWlsZXIKPDwvSW5mbyA4IDAgUi9JRCBbPDUzNWRiNTExMjhlNDJjMmNmNjIxNjYzMTg4NGU3NjA0Pjw4ZjUyZmNkODk3OTE4YTQzOTIwMDM2ZWRkMjVhN2Y1Nz5dL1Jvb3QgNyAwIFIvU2l6ZSA5Pj4Kc3RhcnR4cmVmCjE1MDQKJSVFT0YK";
            byte[] decoder = Base64.getDecoder().decode(b64);

            fos.write(decoder);
            //   System.out.println("PDF SendingFile Saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
