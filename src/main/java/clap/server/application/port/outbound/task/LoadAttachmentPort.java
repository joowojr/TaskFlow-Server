package clap.server.application.port.outbound.task;

import clap.server.domain.model.attachment.Attachment;

import java.util.List;


public interface LoadAttachmentPort {
    List<Attachment> findAllByTaskId(Long taskId);
    List<Attachment> findAllByTaskIdAndAttachmentId(Long taskId, List<Long> attachmentIds);
}
