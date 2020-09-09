package com.unionbankng.future.authorizationserver.repositories;
import com.unionbankng.future.authorizationserver.entities.Tag;
import com.unionbankng.future.authorizationserver.enums.TagType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TagRepository extends JpaRepository<Tag, Long> {
    Page<Tag> findByTypeAndNameLike(TagType type, String likelyName, Pageable pageable);
    Page<Tag> findByType(TagType type,Pageable pageable);
}
