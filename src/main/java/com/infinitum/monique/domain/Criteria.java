package com.infinitum.monique.domain;

public class Criteria {

    private int page;
    private int pageSize;
    private String keyword;

    public Criteria() {
        this.page = 1;
        this.pageSize = 10;
    }

    public int getPage() {
        return page;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setPage(int page) {
        if(page <=0) {
            this.page = 1;
            return;
        }
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if(pageSize <=0 || pageSize > 100) {
            this.pageSize = 10;
            return;
        }
        this.pageSize = pageSize;
    }


    public int getPageStart() {
        return (this.page - 1)*this.pageSize;
    }

    @Override
    public String toString() {
        return "Criteria [page=" + page + ", pageSize=" + pageSize + "]";
    }
}