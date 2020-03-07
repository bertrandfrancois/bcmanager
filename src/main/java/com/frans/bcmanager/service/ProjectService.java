package com.frans.bcmanager.service;

import com.frans.bcmanager.model.Project;
import com.frans.bcmanager.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Service
@Transactional
public class ProjectService implements BaseService<Project>{

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> findAll() {
        return newArrayList(projectRepository.findAll());
    }

    @Override
    public Project save(Project param) {
        return projectRepository.save(param);
    }

    @Override
    public Project find(long id) {
        return projectRepository.findById(id).get();
    }

    @Override
    public void delete(long id) {
        projectRepository.deleteById(id);
    }

    public List<Project> findTop5() {
        return projectRepository.findTop5ByOrderByIdDesc();
    }
}
