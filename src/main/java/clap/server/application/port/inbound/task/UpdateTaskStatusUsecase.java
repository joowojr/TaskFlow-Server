package clap.server.application.port.inbound.task;

import clap.server.domain.model.task.constant.TaskStatus;

public interface UpdateTaskStatusUsecase {
    void updateTaskStatus(Long memberId, Long taskId, TaskStatus taskStatus);
}
