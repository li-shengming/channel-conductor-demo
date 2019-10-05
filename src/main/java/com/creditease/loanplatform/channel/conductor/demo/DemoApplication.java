package com.creditease.loanplatform.channel.conductor.demo;

import com.creditease.loanplatform.channel.conductor.demo.controller.Task1Worker;
import com.creditease.loanplatform.channel.conductor.demo.controller.Task2Worker;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.task.WorkflowTaskCoordinator;
import com.netflix.conductor.client.worker.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by shengmingli 2019/9/24.
 **/
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
@EnableAsync //开启异步调用
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class DemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);
        logger.info("渠道端-服务启动完毕！");

        TaskClient taskClient = new TaskClient();
        taskClient.setRootURI("http://localhost:8080/api/");		//Point this to the server API

        int threadCount = 2;			//number of threads used to execute workers.  To avoid starvation, should be same or more than number of workers

        Worker worker1 = new Task1Worker("user_task");
        Worker worker2 = new Task2Worker("group_task");

        //Create WorkflowTaskCoordinator
        WorkflowTaskCoordinator.Builder builder = new WorkflowTaskCoordinator.Builder();
        WorkflowTaskCoordinator coordinator = builder.withWorkers(worker1, worker2).withThreadCount(threadCount).withTaskClient(taskClient).build();

        //Start for polling and execution of the tasks
        coordinator.init();
        logger.info("worker启动完毕！");
    }

}
