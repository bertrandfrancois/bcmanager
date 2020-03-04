package com.frans.bcmanager.repository;

import com.frans.bcmanager.model.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long>{
}
