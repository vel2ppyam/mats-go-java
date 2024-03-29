package com.vel2ppyam.matsgo.core.common.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URL;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3UploadService {
    private final AmazonS3 s3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadFiles(MultipartFile file, String pathSuffix) throws Exception {
        String newFileName = makeNewFilename(file.getOriginalFilename(), pathSuffix);
        return uploadToS3(file, newFileName, false);

    }

    public PutObjectResult uploadToS3(String bucketName, File file) {
        return s3.putObject(new PutObjectRequest(bucketName, file.getName(), file)
                .withCannedAcl(CannedAccessControlList.BucketOwnerFullControl));
    }


    private String uploadToS3(MultipartFile file, String fileName, boolean isPrivate) throws Exception {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        byte[] resultByte = DigestUtils.md5(file.getBytes());
        String streamMD5 = new String(Base64.encodeBase64(resultByte));
        metadata.setContentMD5(streamMD5);

        s3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata)
                .withCannedAcl(isPrivate ? CannedAccessControlList.Private : CannedAccessControlList.PublicRead));

        return fileName;
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            System.out.println("File delete success");
            return;
        }
        System.out.println("File delete fail");
    }

    public String makeNewFilename(String filename, String pathSuffix) {
        HashCode hash = Hashing.murmur3_128()
                .newHasher()
                .putLong(System.currentTimeMillis())
                .putString(filename, Charsets.UTF_8)
                .hash();

        String fileExt = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();

        return pathSuffix + "/" + hash.toString() + "." + fileExt;
    }

    public String generatePreSignedURL(final String s3ObjectKey) {
        return generatePreSignedURL(s3ObjectKey, 6);
    }

    public String generatePreSignedURL(String s3ObjectKey, int expirationHour) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, s3ObjectKey)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(DateTime.now().plusHours(expirationHour).toDate());
        URL url = s3.generatePresignedUrl(generatePresignedUrlRequest);

        try {
            GetObjectMetadataRequest metadataRequest = new GetObjectMetadataRequest(bucketName, s3ObjectKey);
            ObjectMetadata objectMetadata = s3.getObjectMetadata(metadataRequest);

            log.debug("{}", objectMetadata);

        } catch (Exception e) {
            // log.error(e.getMessage(), e);
        }

        return url.toString();
    }
}
