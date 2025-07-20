package vn.flast.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.flast.entities.FilesInterface;
import vn.flast.utils.Common;
import java.util.List;

@Table(name = "data_media")
@Entity
@Getter @Setter
@NoArgsConstructor
public class DataMedia implements FilesInterface {

    public static String FOLDER_UPLOAD = "/uploads/data";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "data_id")
    private Long dataId;

    @Column(name = "session_id")
    private Long sessionId;

    @Column(name = "file")
    private String file;

    @Override
    public String createFolderUpload() {
        return Common.makeFolder(FOLDER_UPLOAD);
    }

    @Override
    public List<String> filePass() {
        return List.of("png", "jpg", "jpeg");
    }
}
