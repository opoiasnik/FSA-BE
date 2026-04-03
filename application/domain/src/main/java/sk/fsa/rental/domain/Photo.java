package sk.fsa.rental.domain;

public class Photo {
    private Long id;
    private String url;
    private String altText;
    private Listing listing;

    public Photo() {
    }

    public Photo(String url, String altText) {
        this.url = url;
        this.altText = altText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }
}
