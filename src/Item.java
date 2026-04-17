package main;

import java.awt.image.BufferedImage;

public class Item {
    private String name;
    private String details;
    private String pricing;
    private String hashtags;
    private String ownerName;
    private String ownerIdNumber;
    private String status;
    private BufferedImage image;

    public Item(String name, String details, String pricing, String hashtags,
                String ownerName, String ownerIdNumber) {
        this.name = name;
        this.details = details;
        this.pricing = pricing;
        this.hashtags = hashtags;
        this.ownerName = ownerName;
        this.ownerIdNumber = ownerIdNumber;
        this.status = "Available";
        this.image = null;
    }

    public String getName() { return name; }
    public String getDetails() { return details; }
    public String getPricing() { return pricing; }
    public String getHashtags() { return hashtags; }
    public String getOwnerName() { return ownerName; }
    public String getOwnerIdNumber() { return ownerIdNumber; }
    public String getStatus() { return status; }
    public BufferedImage getImage() { return image; }

    public void setStatus(String status) { this.status = status; }
    public void setImage(BufferedImage image) { this.image = image; }
}