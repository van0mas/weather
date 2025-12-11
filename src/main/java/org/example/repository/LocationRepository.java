package org.example.repository;

import org.example.model.Location;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    void deleteByIdAndUserId(Long id, Long userId);
    List<Location> getLocationsByUser(User user);
}
