package com.otl.otl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.otl.otl.domain.Board;

import java.util.List;
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByMemberEmail(String email);
}
