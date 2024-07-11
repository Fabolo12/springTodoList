package org.example.springprojecttodo.controller.mapper;

import org.example.springprojecttodo.bean.TasksResponseBean;
import org.example.springprojecttodo.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {

    TaskMapper INSTANCE  = Mappers.getMapper(TaskMapper.class);

    TasksResponseBean toTasksResponseBean(Task task);
}
