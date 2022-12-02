package BackEnd;

public class Book extends Rental{
    private String pageCount; //length
    private String pageType; //size
    private String language; //resolution

    public Book(String name, String author, String year, String genre, int rent, boolean isAvailableNow,
         String pageCount, String pageType, String language) {
        super(name, author, year, genre, rent, isAvailableNow);
        this.pageCount = pageCount;
        this.pageType = pageType;
        this.language = language;
    }

    public String getPageCount() {
        return pageCount;
    }

    public String getPageType() {
        return pageType;
    }

    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String getLength(){
        return this.pageCount;
    }
    @Override
    public void setLength(String length) {
        this.pageCount = length;
    }

    @Override
    public String getSize(){
        return this.pageType;
    }
    @Override
    public void setSize(String size){
        this.pageType = size;
    }

    @Override
    public String getResolution(){
        return this.language;
    }
    @Override
    public void setResolution(String resolution){
        this.language = resolution;
    }

}
