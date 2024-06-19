package eya.gestiondesstock.portail.repository;

import eya.gestiondesstock.portail.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article , Long> {
    List<Article> findByCategoryId(Long categoryId);
    Optional<Article> findByCodeBarre(String codeBarre);
}
