package com.unionbankng.future.futurejobservice.entities;

import com.unionbankng.future.futurejobservice.enums.ChatFileUploadStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Table(name="chat_files")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatFile implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;
    public String fileId;
    @Column(columnDefinition ="TEXT", nullable=false)
    public String link;
    @Column(columnDefinition ="TEXT", nullable=false)
    public String name;
    public String type;
    public Long size;
    public ChatFileUploadStatus status;
    @Temporal(TemporalType.DATE)
    public Date createdAt;

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }
}
