package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.TestPdfDTO;
import guru.springfamework.domain.TestPdfTable;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TestPdfMapper {

    TestPdfMapper INSTANCE = Mappers.getMapper(TestPdfMapper.class);

    TestPdfDTO testPdfTableToTestPdfDto(TestPdfTable testPdfTable);

    TestPdfTable testPdfTableToPdfTableDTO(TestPdfDTO testPdfDTO);
}
