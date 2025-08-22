package vn.flast.repositories;
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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.Media;
import java.util.List;
import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Integer> {

    @Query("FROM Media m WHERE m.objectId = :objectId AND m.object = :object AND m.status = 1")
    List<Media> listByObjectId(Integer objectId, String object);

    @Query("FROM Media m WHERE m.fileName = :fileName AND m.objectId = :objectId")
    Optional<Media> findFileName(String fileName, Integer objectId);

    @Query("FROM Media m WHERE m.sectionId = :sessionId AND m.object = 'Product'")
    List<Media> listBySessionId(Long sessionId);
}