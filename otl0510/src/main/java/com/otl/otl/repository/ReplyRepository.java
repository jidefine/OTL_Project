package com.otl.otl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.otl.otl.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
