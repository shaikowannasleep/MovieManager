package BackEnd;


public class Movie extends Rental{
    private String length;
    private String size;
    private String resolution;

    public Movie(String name, String author, String year, String genre, int rent, boolean isAvailableNow,
          String length, String size, String resolution){
        super(name, author, year, genre, rent, isAvailableNow);
        this.length = length;
        this.size = size;
        this.resolution = resolution;
    }

    public String getLength() {
        return length;
    }
    public String getSize() {
        return size;
    }
    public String getResolution() {
        return resolution;
    }

    @Override
    public void setLength(String length) {
        this.length = length;
    }

    @Override
    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}
