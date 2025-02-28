package clap.server.application.port.outbound.task;

import clap.server.domain.model.task.Task;

import java.util.List;

public interface CommandTaskDocumentPort {
    void saveStatistics(List<Task> statistics);
}
