// Item.java
package in.sp.itransition.model;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Item name is required")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private String fileName;

    // Mark this field as transient to exclude from persistence
    @Transient
    private MultipartFile file; 

    private String filePath;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] data; // Storing file data as a byte array

    @ManyToOne
    @JoinColumn(name = "collection_id", nullable = false)
    private Collection collection;

    @ManyToMany
    @JoinTable(
        name = "item_tags",
        joinColumns = @JoinColumn(name = "item_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;

    public Item() { // Initialize createdAt during object creation
    }

    // Full constructor
    public Item(Long id, String name, String description, LocalDateTime createdAt, String fileName, 
                MultipartFile file, String filePath, byte[] data, Collection collection, 
                Set<Tag> tags, Set<Comment> comments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = (createdAt == null) ? LocalDateTime.now() : createdAt;
        this.fileName = fileName;
        this.file = file;
        this.filePath = filePath;
        this.data = data;
        this.collection = collection;
        this.tags = tags;
        this.comments = comments;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
