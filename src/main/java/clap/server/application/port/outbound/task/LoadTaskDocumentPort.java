package clap.server.application.port.outbound.task;

import java.util.Map;

public interface LoadTaskDocumentPort {
    Map<String, Long> findPeriodTaskRequestByPeriod(String period);

    Map<String, Long> findPeriodTaskProcessByPeriod(String period);

    Map<String, Long> findCategoryTaskRequestByPeriod(String period);

    Map<String, Long> findSubCategoryTaskRequestByPeriod(String period, String mainCategory);

    Map<String, Long> findManagerTaskProcessByPeriod(String period);
}
