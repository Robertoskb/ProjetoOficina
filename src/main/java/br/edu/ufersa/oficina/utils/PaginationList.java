package br.edu.ufersa.oficina.utils;

import java.util.ArrayList;

public class PaginationList<T> {
    private ArrayList<T> arrayList;
    private int pageSize;

    public PaginationList(ArrayList<T> arrayList, int pageSize) {
        setArrayList(arrayList);
        setPageSize(pageSize);
    }

    public ArrayList<T> getPage(int page){
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, arrayList.size());

        if (fromIndex > arrayList.size())
            return new ArrayList<>();

        return new ArrayList<>(arrayList.subList(fromIndex, toIndex));
    }


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize > 0)
            this.pageSize = pageSize;
    }

    public ArrayList<T> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<T> arrayList) {
        this.arrayList = arrayList;
    }
}
