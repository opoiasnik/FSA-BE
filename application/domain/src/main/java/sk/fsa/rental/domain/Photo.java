package sk.fsa.rental.domain;

public class Photo {
    private Long id;
    private String altText;
    private String contentType;
    private String originalFilename;
    private Integer position;
    private byte[] data;
    private Listing listing;

    public Photo() {
    }

    public Photo(byte[] data, String contentType, String originalFilename, String altText, Integer position) {
        this.data = data;
        this.contentType = contentType;
        this.originalFilename = originalFilename;
        this.altText = altText;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void updateDetails(String altText, Integer position) {
        this.altText = altText;
        this.position = position;
    }

    public String getAltText() { return altText; }
    public String getContentType() { return contentType; }
    public String getOriginalFilename() { return originalFilename; }
    public Integer getPosition() { return position; }
    public byte[] getData() { return data; }
    public Listing getListing() { return listing; }

    // called by Listing.addPhoto() to wire the relationship
    void setListing(Listing listing) { this.listing = listing; }
}
