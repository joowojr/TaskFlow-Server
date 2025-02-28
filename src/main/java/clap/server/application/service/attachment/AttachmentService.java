package clap.server.application.service.attachment;

import clap.server.application.port.outbound.s3.S3UploadPort;
import clap.server.application.port.outbound.task.CommandAttachmentPort;
import clap.server.common.constants.FilePathConstants;
import clap.server.domain.model.task.Attachment;
import clap.server.domain.model.task.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.IntStream;

import static clap.server.domain.model.task.Attachment.createAttachment;

@Service
@RequiredArgsConstructor
public class AttachmentService {
    private final S3UploadPort s3UploadPort;
    private final CommandAttachmentPort commandAttachmentPort;

    @Transactional
    public int saveTaskAttachments(Task task, List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            return 0;
        }
        List<String> fileUrls = s3UploadPort.uploadFiles(FilePathConstants.TASK_FILE, files);
        List<Attachment> attachments = toTaskAttachments(task, files, fileUrls);
        commandAttachmentPort.saveAll(attachments);
        return fileUrls.size();
    }

    public String uploadCommentAttachment(MultipartFile file) {
        return s3UploadPort.uploadSingleFile(FilePathConstants.TASK_COMMENT, file);
    }

    public String uploadMemberProfileImage(MultipartFile file) {
        return s3UploadPort.uploadSingleFile(FilePathConstants.MEMBER_IMAGE, file);
    }

    public static List<Attachment> toTaskAttachments(Task task, List<MultipartFile> files, List<String> fileUrls) {
        return IntStream.range(0, files.size())
                .mapToObj(i -> createAttachment(
                        task,
                        files.get(i).getOriginalFilename(),
                        fileUrls.get(i),
                        files.get(i).getSize()
                ))
                .toList();
    }
}
