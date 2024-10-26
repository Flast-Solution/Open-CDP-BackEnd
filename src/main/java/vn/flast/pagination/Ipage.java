
package vn.flast.pagination;

import java.util.List;

public class Ipage <T> {
    
    private PageDetail page;
    private final List<T> embedded;
    
    private final int pageSize;
    private final long totalElements;
    private final int total;
    
    public Ipage(int size, long totalElements, int number, List<T> embedded) {
        this.pageSize = size;
        this.totalElements = totalElements;
        this.embedded = embedded;
        if (totalElements % size == 0) {
            this.total = (int) totalElements/size;
        } else {
            this.total = (int) (totalElements/size) + 1;
        }
        this.setPage();
    }

    public static Ipage<?> generator(int size, long totalElements, int currentPage, List<?> embedded) {
        return new Ipage<>(size, totalElements, currentPage, embedded);
    }


    private void setPage() {
        this.page = new PageDetail(this.pageSize, this.totalElements, total);
    }

    public PageDetail getPage() {
        return page;
    }
    
    public List<T> getEmbedded() {
        return embedded;
    }
}
