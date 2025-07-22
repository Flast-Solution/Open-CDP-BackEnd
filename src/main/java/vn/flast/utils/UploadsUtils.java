package vn.flast.utils;

import vn.flast.entities.FilesInterface;
import vn.flast.exception.InvalidParamsException;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class UploadsUtils {

    public static String upload(
        FilesInterface uploadFiles,
        MultipartFile multipartFile
    ) throws Exception {

        Objects.requireNonNull(multipartFile, "Multipart not empty .!");
        String nameFile = multipartFile.getOriginalFilename();
        String extensionFile = extension(Objects.requireNonNull(nameFile));
        List<String> filePass = uploadFiles.filePass();
        if(!filePass.isEmpty() && !filePass.contains(extensionFile)) {
            throw new InvalidParamsException("File không được phép upload !");
        }

        String subFolder = uploadFiles.createFolderUpload();
        String fd = System.getProperty("user.dir") + subFolder + "/";

        String fileDeAccent = deAccent(nameFile);
        String filePath = fd + fileDeAccent;
        InputStream fileStream = multipartFile.getInputStream();
        File targetFile = new File(filePath);

        FileUtils.copyInputStreamToFile(fileStream, targetFile);
        return subFolder.concat(fileDeAccent);
    }

    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String ret = pattern.matcher(nfdNormalizedString).replaceAll("")
                .replaceAll("Đ", "D").replace("đ", "");
        return ret.replace(" ", "_");
    }

    private static String extension(String name) {
        String[] fileFrags = name.split("\\.");
        try {
            return fileFrags[fileFrags.length - 1];
        } catch (Exception e) {
            return "txt";
        }
    }
}
