package guru.springfamework.repositories;

import guru.springfamework.domain.SignData;

import java.util.List;

public interface EdocRepository {
    List<SignData> findAll();
}
