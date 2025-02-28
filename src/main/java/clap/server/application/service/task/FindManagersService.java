package clap.server.application.service.task;

import clap.server.adapter.inbound.web.dto.task.response.FindManagersResponse;
import clap.server.application.mapper.response.TaskResponseMapper;
import clap.server.application.port.inbound.task.FindManagersUsecase;
import clap.server.application.port.outbound.member.LoadMemberPort;
import clap.server.common.annotation.architecture.ApplicationService;
import clap.server.domain.model.member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationService
@RequiredArgsConstructor
public class FindManagersService implements FindManagersUsecase {
    private final LoadMemberPort loadMemberPort;

    @Transactional
    @Override
    public List<FindManagersResponse> findManagers() {
        List<Member> managers = loadMemberPort.findActiveManagers();
        return managers.stream()
                .map(TaskResponseMapper::toFindManagersResponse).toList();
    }
}
;