package com.pioneer.workflow.service;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ActivitiService {

    /**
     * 注入为我们自动配置好的服务
     */
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    /**
     * 开始流程，传入申请者的id以及公司的id
     *
     * @param personId
     * @param compId
     */
    public void startProcess(Long personId, Long compId) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("personId", personId);
        variables.put("compId", compId);
        runtimeService.startProcessInstanceByKey("joinProcess", variables);
    }

    /**
     * 获得某个人的任务别表
     *
     * @param assignee
     * @return
     */
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskCandidateUser(assignee).list();
    }

    /**
     * 完成任务
     *
     * @param joinApproved
     * @param taskId
     */
    public void completeTasks(Boolean joinApproved, String taskId) {
        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("joinApproved", joinApproved);
        taskService.complete(taskId, taskVariables);
    }
}