
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




import java.io.Serial;
import java.io.Serializable;

public class PageDetail implements Serializable {

	@Serial
    private static final long serialVersionUID = -5302862214964526552L;
	public int pageSize;
    public long totalElements;
    public int total;
    
    public PageDetail(int size, long totalElements, int total) {
        this.pageSize = size;
        this.totalElements = totalElements;
        this.total = total;
    }
    
    public void setPageSize(int size) {
        this.pageSize = size;
    }
    
    public void setTotalElements( long ele) {
        this.totalElements = ele;
    }
    
    public void setTotal(int ttp) {
        this.total = ttp;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotal() {
        return total;
    }
}
