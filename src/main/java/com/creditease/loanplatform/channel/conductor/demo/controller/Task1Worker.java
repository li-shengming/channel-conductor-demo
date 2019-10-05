package com.creditease.loanplatform.channel.conductor.demo.controller;

import com.alibaba.fastjson.JSON;
import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;

/**
 * Created by shengmingli 2019/9/27.
 **/
public class Task1Worker implements Worker {
    private String taskDefName;

    public Task1Worker(String taskDefName) {
        this.taskDefName = taskDefName;
    }

    @Override
    public String getTaskDefName() {
        return taskDefName;
    }

    @Override
    public TaskResult execute(Task task) {

        System.out.printf("Executing %s%n", taskDefName);
        System.out.println();
        System.out.printf("task："+ JSON.toJSONString(task));
        System.out.println();
        TaskResult result = new TaskResult(task);
        result.setStatus(TaskResult.Status.COMPLETED);

        //Register the output of the task
        if(task.getInputData().containsKey("type")){
            if("1".equals(task.getInputData().get("type").toString())){
                result.getOutputData().put("id", "1");
            }else{
                result.getOutputData().put("id", "-1");
            }
        }else{
            result.getOutputData().put("id", "-1");
        }
        System.out.printf("task response："+ JSON.toJSONString(result));
        System.out.println();
        return result;
    }
}
