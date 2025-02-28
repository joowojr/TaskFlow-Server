package clap.server.domain.model.log;

import clap.server.adapter.outbound.persistense.entity.log.constant.LogStatus;
import clap.server.domain.model.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
public class MemberLog extends ApiLog {
    private Member member;

    public static MemberLog createMemberLog(String clientIp, String requestUrl, String requestMethod, int statusCode, String customCode, LogStatus logStatus, Object responseBody, String requestBody, Member member) {
        return MemberLog.builder()
                .clientIp(clientIp)
                .requestUrl(requestUrl)
                .requestMethod(requestMethod)
                .statusCode(statusCode)
                .customStatusCode(customCode != null ? customCode : "")
                .requestBody(requestBody)
                .responseBody(responseBody != null ? responseBody.toString() : logStatus.getDescription())
                .requestAt(LocalDateTime.now())
                .logStatus(logStatus)
                .member(member).build();
    }
}