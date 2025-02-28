package clap.server.adapter.inbound.web.dto.notification.request;

import clap.server.domain.model.notification.constant.NotificationType;

public record SseRequest(
        String taskTitle,
        NotificationType notificationType,
        Long receiverId,
        String message
) {
}
