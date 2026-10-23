package org.example.webhookhub.project;

import org.example.webhookhub.project.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectResponse createdProject(CreateProjectRequest request){

        Project project = new Project(request.name());

        Project savedProject = projectRepository.save(project);

        return mapToResponse(savedProject);
    }

    public List<ProjectResponse> getAllProjects(){
        return projectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProjectResponse getProjectById(UUID id){
        Project project = projectRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Project not found"));

        return mapToResponse(project);
    }

    private ProjectResponse mapToResponse(Project project){
        return  new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getCreatedAt()
        );
    }
}
