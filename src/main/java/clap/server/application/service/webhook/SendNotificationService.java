package clap.server.application.service.webhook;

import clap.server.adapter.outbound.api.data.PushNotificationTemplate;
import clap.server.adapter.outbound.persistense.entity.notification.constant.NotificationType;
import clap.server.application.port.outbound.notification.CommandNotificationPort;
import clap.server.common.annotation.architecture.ApplicationService;
import clap.server.domain.model.member.Member;
import clap.server.domain.model.notification.Notification;
import clap.server.domain.model.task.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;

import static clap.server.domain.model.notification.Notification.createTaskNotification;

@ApplicationService
@RequiredArgsConstructor
public class SendNotificationService {

    @Value("${redirect.url.user}")
    private String REDIRECT_URL_USER;

    @Value("${redirect.url.task.request}")
    private String REDIRECT_URL_TASK_REQUEST;

    @Value("${redirect.url.manger}")
    private String REDIRECT_URL_MANAGER;

    private final SendAgitService sendAgitService;
    private final NotificationSender notificationSender;
    private final CommandNotificationPort commandNotificationPort;

    @Async("notificationExecutor")
    public void sendPushNotification(Member receiver, NotificationType notificationType,
                                     Task task, String message, String reason, String commenterName, Boolean isManager) {

        String taskDetailUrl = extractTaskUrl(notificationType, task, isManager);

        Notification notification = createTaskNotification(task, receiver, notificationType, message, task.getTitle());

        commandNotificationPort.save(notification);

        notificationSender.sendNotification(receiver, notificationType, task, message, reason, commenterName, taskDetailUrl);
    }

    @Async("notificationExecutor")
    public void sendAgitNotification(NotificationType notificationType,
                                     Task task, String message, String commenterName) {
        PushNotificationTemplate pushNotificationTemplate = new PushNotificationTemplate(
                null,
                notificationType,
                task.getTitle(),
                task.getRequester().getNickname(),
                message,
                commenterName,
                null
        );

        String taskDetailUrl = extractTaskUrl(notificationType, task, true);

        sendAgitService.sendAgit(pushNotificationTemplate, task, taskDetailUrl);
    }

    private String extractTaskUrl(NotificationType notificationType, Task task, Boolean isManager) {
        String taskDetailUrl = REDIRECT_URL_USER + task.getTaskId();
        if (isManager) {
            if (notificationType == NotificationType.TASK_REQUESTED) {
                taskDetailUrl = REDIRECT_URL_TASK_REQUEST + task.getTaskId();
            } else {
                taskDetailUrl = REDIRECT_URL_MANAGER + task.getTaskId();
            }
        }
        return taskDetailUrl;
    }
}
