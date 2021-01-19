package guru.springfamework.api.v1.TestPdf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(TestPdfController.BASE_URL)
public class TestPdfController implements TestPdfApi{

    public static final String BASE_URL = "/api/v1/report";

    private final TestPdfApiDelegate testPdfApiDelegate;

    public TestPdfController(@Autowired(required = false)TestPdfApiDelegate testPdfApiDelegate) {
        this.testPdfApiDelegate = Optional
                .ofNullable(testPdfApiDelegate)
                .orElse(new TestPdfApiDelegate() {});
    }

    @Override
    public TestPdfApiDelegate getDelegate() {
        return testPdfApiDelegate;
    }
}
