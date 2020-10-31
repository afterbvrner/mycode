package ru.teamnull.mycode.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.teamnull.mycode.entity.Group;
import ru.teamnull.mycode.entity.Task;
import ru.teamnull.mycode.repository.GroupRepository;
import ru.teamnull.mycode.repository.TaskRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final GroupRepository groupRepository;

    public Set<Task> getTasksByGroupId(UUID groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getTasks();
    }

    public Task getById(UUID groupId, UUID taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(task.getGroups()
                .stream()
                .anyMatch(group -> group.getId().equals(groupId))) {
            return task;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteById(UUID id) {
        taskRepository.deleteById(id);
    }
}