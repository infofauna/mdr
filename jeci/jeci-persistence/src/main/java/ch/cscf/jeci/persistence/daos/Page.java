package ch.cscf.jeci.persistence.daos;

import java.io.Serializable;

/**
 * This class is used to achieve pagination when listing entities.
 * It encapsulates all the information needed to load only a part of a list of entities.
 * @author Pierre Henry
 */
public class Page implements Serializable {

    /**
     * Page number
     */
    private Integer pageNumber=1;

    /**
     * Number of pages to display before and after the current page
     */
    private Integer numOfPagesToDisplayBeforeAfter=5;

    /**
     * Number of elements per page
     */
    private Integer pageSize=20;

    /**
     * Total number of rows in the set
     */
    private Integer totalRows=null;

    public String toString(){
        return "Page "+pageNumber+"/"+getLastPage();
    }

    /**
     * @return The last page number possible, based on the total number of rows and the number of rows per page.
     */
    public Integer getLastPage(){
        if(totalRows == null) return 1;
        return (totalRows%pageSize == 0) ? totalRows/pageSize : totalRows/pageSize +1;
    }

    public Integer getFirstPageToDisplay(){
        return Math.max(1, this.pageNumber-numOfPagesToDisplayBeforeAfter);
    }

    public Integer getLastPageToDisplay(){
        return Math.min(getLastPage(), this.pageNumber+numOfPagesToDisplayBeforeAfter);
    }


    /**
     * Gets the global position (zero based) of the first row of this page.
     * Eg: if the page number is 3 and the page size is 20, it will be 40
     * @return
     */
    public Integer getFirstRow(){
        return (pageNumber-1)*pageSize;
    }

    /**
     * Gets the global position (zero based) of the last row of this page.
     * Eg: if the page number is 3 and the page size is 20, it will be 59
     * @return
     */
    public Integer getLastRow(){
        return Math.min((pageNumber)*pageSize-1, totalRows-1);
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    /**
     * Sets the page number.
     * If the total number of rows is set, it checks that the given page number is valid.
     * @param pageNumber
     */
    public void setPageNumber(Integer pageNumber) {
        if(totalRows == null){
            this.pageNumber = pageNumber;
        }else{
            this.pageNumber = Math.min(pageNumber, getLastPage());
        }
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }
}
