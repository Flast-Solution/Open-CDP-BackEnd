
package vn.flast.pagination;

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
