package br.com.igbr.portfolioApi.repository;

import br.com.igbr.portfolioApi.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
}
