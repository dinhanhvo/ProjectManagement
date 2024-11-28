package com.vodinh.prime.listeners;

import com.vodinh.prime.entities.Project;
import jakarta.persistence.*;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectEntityListener {

    private static RedissonClient redissonClient;

    @Autowired
    public void setRedissonClient(RedissonClient client) {
        redissonClient = client;
    }

    @PostPersist
    public void afterInsert(Project project) {
        RMap<Long, Project> projectCache = redissonClient.getMap("projects");
        projectCache.put(project.getId(), project);
    }

    @PostUpdate
    public void afterUpdate(Project project) {
        RMap<Long, Project> projectCache = redissonClient.getMap("projects");
        projectCache.put(project.getId(), project);
    }

    @PostRemove
    public void afterDelete(Project project) {
        RMap<Long, Project> projectCache = redissonClient.getMap("projects");
        projectCache.remove(project.getId());
    }
}
