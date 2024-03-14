package univcapstone.employmentsite.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.util.response.BasicResponse;
import univcapstone.employmentsite.util.response.DefaultResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class PictureService {

    private final AmazonS3 amazonS3;

    @Value("jobhakdasik2000-bucket")
    private String bucket;

    /*
    public PutObjectResult uploadFile(String fileName, File file, ObjectMetadata metadata){
        metadata.setContentLength(file.length());
        metadata.setContentDisposition(MediaType.IMAGE_JPEG_VALUE);
        metadata.setContentType(MediaType.IMAGE_JPEG_VALUE);
        PutObjectResult result= amazonS3.putObject(
                new PutObjectRequest(bucket, fileName, file).withMetadata(metadata)
        );
        return result;
    }
     */
    public ResponseEntity<Object> uploadMultipartFile(String fileName, MultipartFile[] multipartFileList) throws IOException {
        List<String> imagePathList = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFileList) {

            ObjectMetadata objectMetaData = new ObjectMetadata();
            objectMetaData.setContentType(MediaType.IMAGE_JPEG_VALUE);

            // S3에 업로드
            amazonS3.putObject(
                    new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), objectMetaData)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );

            String imagePath = amazonS3.getUrl(bucket, fileName).toString(); // 접근가능한 URL 가져오기
            imagePathList.add(imagePath);
        }

        return new ResponseEntity<Object>(imagePathList, HttpStatus.OK);
    }

    public ResponseEntity<File> getObject(String storedFileName) throws IOException {
        S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucket, storedFileName));
        InputStream inputStream = s3Object.getObjectContent();
        File file = new File(storedFileName);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            IOUtils.copy(inputStream, outputStream);
        }

        String encodedFileName = URLEncoder.encode(storedFileName, "UTF-8").replaceAll("\\+", "%20");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
        httpHeaders.setContentDispositionFormData("attachment", encodedFileName);

        return new ResponseEntity<>(file, httpHeaders, HttpStatus.OK);
    }

    public ResponseEntity<? extends BasicResponse> getImage(User user) {
        URL url = amazonS3.getUrl(bucket, Long.toString(user.getId()));
        String urltext = "" + url;
        StringBuilder images = new StringBuilder();
        images.append("<img src='").append(urltext).append("' />");
        DefaultResponse<String> defaultResponse = DefaultResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("아마존 S3로부터 가져온 이미지 url")
                .result(images.toString())
                .build();

        return ResponseEntity.ok()
                .body(defaultResponse);
    }
}
