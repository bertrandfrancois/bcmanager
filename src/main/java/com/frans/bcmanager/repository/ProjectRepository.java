package com.frans.bcmanager.repository;

import com.frans.bcmanager.model.Document;
import com.frans.bcmanager.model.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long>{
    List<Project> findTop5ByOrderByIdDesc();


}
