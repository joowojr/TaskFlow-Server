package clap.server.domain.model.log;

import clap.server.domain.model.log.constant.LogStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
public class AnonymousLog extends ApiLog {
    private String loginNickname;

    public static AnonymousLog createAnonymousLog(String clientIp, String requestUrl, String requestMethod, int statusCode, String customCode, LogStatus logStatus, Object responseBody, String requestBody, String nickName) {
        return AnonymousLog.builder()
                .clientIp(clientIp)
                .requestUrl(requestUrl)
                .requestMethod(requestMethod)
                .statusCode(statusCode)
                .customStatusCode(customCode != null ? customCode : "")
                .requestBody(requestBody)
                .responseBody(responseBody != null ? responseBody.toString() : "로그인 실패")
                .requestAt(LocalDateTime.now())
                .logStatus(logStatus)
                .loginNickname(nickName)
                .build();
    }
}