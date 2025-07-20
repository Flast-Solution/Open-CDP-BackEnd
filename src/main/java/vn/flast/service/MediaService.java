package vn.flast.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.flast.models.Media;
import vn.flast.repositories.MediaRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;

    public List<Media> list(Integer objectId, String object) {
        return mediaRepository.listByObjectId(objectId, object);
    }

    public List<Media> listSessionId(Long sessionId){
        return mediaRepository.listBySessionId(sessionId);
    }

    public void removeFileProduct(String file, Integer productId){
        var media = mediaRepository.findFileName(file, productId).orElseThrow(
            () -> new RuntimeException("Không tồn tại bản ghi này")
        );
        media.setStatus(Media.NOT_ACTIVE);
        mediaRepository.save(media);
    }
}
