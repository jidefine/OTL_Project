package com.otl.otl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.otl.otl.domain.Board;

import java.util.List;
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByMemberEmail(String email); // email이 있는 경우(회원인 경우)

    Page<Board> findByIsDeletedFalse(Pageable pageable); //isDeleted가 false인 데이터만 가져옴
}
