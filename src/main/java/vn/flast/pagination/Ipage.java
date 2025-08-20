
package vn.flast.pagination;
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

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ipage <T> {
    
    @Getter
    private PageDetail page;

    @Getter
    private final List<T> embedded;
    
    private final int pageSize;
    private final long totalElements;
    private final int total;
    
    public Ipage(int size, long totalElements, int number, List<T> embedded) {
        this.pageSize = size;
        this.totalElements = totalElements;
        this.embedded = Objects.isNull(embedded) ? new ArrayList<>() : embedded;
        if (totalElements % size == 0) {
            this.total = (int) totalElements/size;
        } else {
            this.total = (int) (totalElements/size) + 1;
        }
        this.setPage();
    }

    public static <E> Ipage<E> generator(int size, long totalElements, int currentPage, List<E> embedded) {
        return new Ipage<>(size, totalElements, currentPage, embedded);
    }

    public static Ipage<?> empty() {
        return new Ipage<>(0, 0, 1, new ArrayList<>());
    }

    private void setPage() {
        this.page = new PageDetail(this.pageSize, this.totalElements, total);
    }
}
