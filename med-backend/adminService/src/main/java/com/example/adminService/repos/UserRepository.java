package com.example.adminService.repos;

import com.example.adminService.kafka.models.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<AppUser,Integer> {
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByRole(String role);

    Page<AppUser> findByRoleNot(String role,Pageable pageable);

//    @Query(value = "select u from users u where (Lower(name) like '%:query%') or (Lower(username) like '%:query%') or (Lower(role) like '%:query%')")
//    Page<AppUser> searchUserByQuery(String query, Pageable pageable);

    Page<AppUser> findAllByNameContainingIgnoreCase(String query,Pageable pageable);
}
