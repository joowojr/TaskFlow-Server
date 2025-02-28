package clap.server.application.service.webhook;

import clap.server.adapter.outbound.api.data.PushNotificationTemplate;
import clap.server.domain.model.notification.constant.NotificationType;
import clap.server.domain.model.member.Member;
import clap.server.domain.model.task.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationSender {

    private final SendWebhookEmailService sendWebhookEmailService;
    private final SendKaKaoWorkService sendKaKaoWorkService;

    @Async("notificationExecutor")
    public void sendNotification(Member receiver, NotificationType notificationType,
                                 Task task, String message, String reason, String commenterName, String taskDetailUrl) {

        PushNotificationTemplate template = new PushNotificationTemplate(
                receiver.getMemberInfo().getEmail(),
                notificationType,
                task.getTitle(),
                task.getRequester().getNickname(),
                message,
                commenterName,
                reason
        );

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        if (receiver.getEmailNotificationEnabled()) {
            futures.add(CompletableFuture.runAsync(() -> sendWebhookEmailService.send(template, taskDetailUrl)));
        }

        if (receiver.getKakaoworkNotificationEnabled()) {
            futures.add(CompletableFuture.runAsync(() -> sendKaKaoWorkService.send(template, taskDetailUrl)));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
}

