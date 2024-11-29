package com.suvash.chirkutt.Repository;

import com.suvash.chirkutt.Model.Keyword;
import com.suvash.chirkutt.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    List<Keyword> findByUser(User user);
}
