package vn.flast.service;
/**************************************************************************/
/*  app.java                                                              */
/**************************************************************************/
/*                       Tệp này là một phần của:                         */
/*                             Open CDP                                   */
/*                        https://flast.vn                                */
/**************************************************************************/
/* Bản quyền (c) 2025 - này thuộc về các cộng tác viên Flast Solution     */
/* (xem AUTHORS.md).                                                      */
/* Bản quyền (c) 2024-2025 Long Huu, Thành Trung                          */
/*                                                                        */
/* Bạn được quyền sử dụng phần mềm này miễn phí cho bất kỳ mục đích nào,  */
/* bao gồm sao chép, sửa đổi, phân phối, bán lại…                         */
/*                                                                        */
/* Chỉ cần giữ nguyên thông tin bản quyền và nội dung giấy phép này trong */
/* các bản sao.                                                           */
/*                                                                        */
/* Đội ngũ phát triển mong rằng phần mềm được sử dụng đúng mục đích và    */
/* có trách nghiệm                                                        */
/**************************************************************************/




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
