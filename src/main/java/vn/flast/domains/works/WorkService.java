package vn.flast.domains.works;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.flast.components.RecordNotFoundException;
import vn.flast.entities.WorkResponse;
import vn.flast.models.FlastProjectList;
import vn.flast.models.FlastProjectTask;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.FlastProjectListRepository;
import vn.flast.repositories.FlastProjectTaskRepository;
import vn.flast.searchs.WorkFilter;
import vn.flast.utils.EntityQuery;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkService {

    private final FlastProjectListRepository flastProjectListRepository;
    private final FlastProjectTaskRepository flastProjectTaskRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Ipage<FlastProjectList> fetch(WorkFilter filter) {
        int LIMIT = 10;
        int PAGE = filter.page();

        var et = EntityQuery.create(entityManager, FlastProjectList.class);
        et.longEqualsTo("managerId", filter.userId());
        et.integerEqualsTo("status", filter.status());
        et.setMaxResults(LIMIT);
        et.setFirstResult(LIMIT * PAGE);

        var lists = et.list();
        return Ipage.generator(LIMIT, et.count(), PAGE, lists);
    }

    public FlastProjectList save(FlastProjectList model) {
        return flastProjectListRepository.save(model);
    }

    public FlastProjectTask saveTask(FlastProjectTask model) {
        return flastProjectTaskRepository.save(model);
    }

    public WorkResponse findId(Integer id) {
        var flastWork = flastProjectListRepository.findById(id).orElseThrow(
            () -> new RecordNotFoundException("Not Found")
        );
        List<FlastProjectTask> taskList = flastProjectTaskRepository
            .isEqual("projectId", flastWork.getId())
            .findAll(0, 200);
        return new WorkResponse(flastWork, taskList);
    }
}
