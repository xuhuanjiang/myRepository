package cn.itcast.travel.domain;

import java.util.List;

public class PageBean<T> {
    private int totalPage; //总页数
    private int totalCount;//总记录数
    private int pageNumber;//当前页码
    private int pageSize;//页面记录数
    private List<T> list;

    public PageBean() {
    }

    public PageBean(int totalPage, int totalCount, int pageNumber, int pageSize, List<T> list) {
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.list = list;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalPage=" + totalPage +
                ", totalCount=" + totalCount +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", list=" + list +
                '}';
    }
}
