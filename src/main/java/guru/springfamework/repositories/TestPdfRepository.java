package guru.springfamework.repositories;

import guru.springfamework.domain.TestPdfTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestPdfRepository extends JpaRepository<TestPdfTable, Long> {

}
