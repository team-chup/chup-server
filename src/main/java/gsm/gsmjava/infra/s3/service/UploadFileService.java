package gsm.gsmjava.infra.s3.service;

import gsm.gsmjava.infra.s3.properties.S3Environment;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Exception;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadFileService {

    private final S3Template s3Template;
    private final S3Environment s3Environment;

    public String execute(MultipartFile multipartFile, String fileExtension, String directory) {
        String fileName = generateFileName(fileExtension);

        try {
            S3Resource s3Resource =  s3Template.upload(
                    s3Environment.bucketName(),
                    directory + "/" + fileName,
                    multipartFile.getInputStream(),
                    ObjectMetadata.builder().contentType(fileExtension).build()
            );

            return s3Resource.getURL().toString();
        } catch (IOException e) {
            throw new RuntimeException("입출력 작업중에 예외 발생", e);
        } catch (S3Exception e) {
            throw new RuntimeException("S3에서 예외 발생", e);
        } catch (AwsServiceException e) {
            throw new RuntimeException("AWS에서 예외 발생", e);
        } catch (SdkClientException e) {
            throw new RuntimeException("클라이언측 문제로 인한 예외 발생", e);
        }
    }

    private String generateFileName(String fileExtension) {
        return UUID.randomUUID().toString() + LocalDateTime.now() + "." + fileExtension;
    }
}
