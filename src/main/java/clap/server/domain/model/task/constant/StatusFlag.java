package clap.server.domain.model.task.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusFlag {
    ACTIVE("활성"),
    INACTIVE("비활성");

    private final String description;
}
