package com.suvash.chirkutt.Repository;

import com.suvash.chirkutt.Model.Message;
import com.suvash.chirkutt.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByUser(User user);
}
