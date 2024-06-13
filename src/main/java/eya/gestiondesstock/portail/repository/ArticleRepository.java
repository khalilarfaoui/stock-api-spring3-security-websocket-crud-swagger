package eya.gestiondesstock.portail.repository;

import eya.gestiondesstock.portail.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article , Long> {
    List<Article> findByCategoryId(Long categoryId);
}
