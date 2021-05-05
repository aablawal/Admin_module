package com.unionbankng.future.futureloanservice.repositories;
import com.unionbankng.future.futureloanservice.entities.ChatFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatFileUploadRepository extends JpaRepository<ChatFile,Long> {
}
