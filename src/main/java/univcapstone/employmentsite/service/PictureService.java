package univcapstone.employmentsite.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class PictureService {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucket;

    public List<String> uploadFile(List<MultipartFile> multipartFiles, String dirName) throws IOException {

        if (multipartFiles.isEmpty()) {
            log.error("업로드한 파일이 존재하지 않습니다.");
            throw new FileNotFoundException("업로드한 파일이 존재하지 않습니다.");
        }

        //사용자가 업로드한 파일들의 URI를 저장하는 자료구조
        List<String> imagePathList = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {

            File file = convertToFile(multipartFile);

            //S3에 올릴 파일명(UUID 이용)
            String uploadFilename = dirName + UUID.randomUUID() + file.getName();
            log.info("uploadFilename = {}", uploadFilename);

            //S3에 업로드
            String imagePath = uploadS3(uploadFilename, file);
            imagePathList.add(imagePath);

            //S3 업로드 후 로컬에 저장된 사진 삭제
            removeCreatedFile(file);
        }

        return imagePathList;
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

    //AWS S3에 사용자가 업로드한 사진을 업로드한다.
    private String uploadS3(String uploadFilename, File file) {
        amazonS3.putObject(new PutObjectRequest(bucket, uploadFilename, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        log.info("uploadFilename = {}", uploadFilename);

        String imagePath = amazonS3.getUrl(bucket, uploadFilename).toString(); // 접근가능한 URL 가져오기
        return imagePath;
    }

    //MultipartFile to File
    //업로드할때 파일이 로컬에 없으면 에러가 발생하기 때문에 입력받은 파일을 로컬에 저장하고 업로드해야 함
    //따라서 S3에 업로드 이후 로컬에 저장된 사진을 삭제해야 함
    private File convertToFile(MultipartFile multipartFile) throws IOException {
        //확장자를 포함한 파일 이름을 가져온다.
        String filename = multipartFile.getOriginalFilename();
        log.info("filename = {}", filename);

        File file = new File("src/main/resources/temp/" + filename);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(multipartFile.getBytes());

        return file;
    }

    //로컬에 저장된 사진 삭제
    private void removeCreatedFile(File createdFile) {
        if (createdFile.delete()) {
            log.info("Created File delete success");
            return;
        }

        log.info("Created File delete fail");
    }

}
