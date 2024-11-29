package com.vodinh.prime.listeners;

import com.vodinh.prime.controller.ProjectController;
import com.vodinh.prime.entities.Project;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProjectEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ProjectEventListener.class);

    private final RedissonClient redissonClient;

    public ProjectEventListener(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    private void updateRedisCache(Project project) {
        // Distributed Lock
        RLock lock = redissonClient.getLock("project-lock:" + project.getId());
        try {
            lock.lock();
            RMap<Long, Project> projectCache = redissonClient.getMap("projects");
            projectCache.remove(project.getId()); // avoid racing in case of using multi instances
            projectCache.put(project.getId(), project);
            System.out.println("Updated Redis cache for project: " + project.getName());

        } catch (Exception _) {
        } finally {
            lock.unlock();
        }
    }

//    private void updateRedisCache(List<Project> projects) {
//        Map<Long, Project> map = projects.stream().collect(Collectors.toMap(Project::getId, Function.identity()));
//        RMap<Long, Project> projectCache = redissonClient.getMap("projects");
//        projectCache.putAll(map);
//    }

    @PrePersist
    public void beforeSave(Project project) {
        System.out.println("Before saving project: " + project.getName());
        // Do nothing
    }

    @PostPersist
    public void afterSave(Project project) {
        logger.info(STR."After saving project: \{project.getName()}");
        if (Objects.nonNull(project.getId())) {
            logger.info(STR."update project: \{project.getName()}");
            updateRedisCache(project);
        } else {
            logger.info(STR."creating project: \{project.getName()}");
            // just save
            RMap<Long, Project> projectCache = redissonClient.getMap("projects");
            projectCache.put(project.getId(), project);
        }
    }
}

