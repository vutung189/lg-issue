package com.app.repository;


import com.app.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	String USERS_BY_LOGIN_CACHE = "usersByLogin";



	@Query("SELECT u.login FROM User u WHERE u.id IN ?1")
	List<String> findAllLoginById(List<Long> ids);

	Optional<User> findOneByLogin(String login);

	@EntityGraph(attributePaths = "authorities")
	Optional<User> findOneWithAuthoritiesById(Long id);

	@EntityGraph(attributePaths = "authorities")
	@Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
	Optional<User> findOneWithAuthoritiesByLogin(String login);

}
