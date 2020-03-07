package com.frans.bcmanager.repository;

import com.frans.bcmanager.model.Client;
import com.frans.bcmanager.model.Document;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
    List<Client> findTop5ByOrderByIdDesc();

}
