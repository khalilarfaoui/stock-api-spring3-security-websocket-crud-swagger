package eya.gestiondesstock.portail.controller;

import eya.gestiondesstock.portail.entity.Article;
import eya.gestiondesstock.portail.entity.Category;
import eya.gestiondesstock.portail.entity.dto.AddArticleDTO;
import eya.gestiondesstock.portail.repository.ArticleRepository;
import eya.gestiondesstock.portail.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        Optional<Article> article = articleRepository.findById(id);
        return article.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createArticle(@RequestBody AddArticleDTO addArticleDTO) {
        Category category = categoryRepository.findById(addArticleDTO.getCategoryId()).orElse(null);
        if(category == null){
          return ResponseEntity.badRequest().body("category not found");
        }
        Article article = new Article();
        article.setName(addArticleDTO.getName());
        article.setCodeBarre(addArticleDTO.getCodeBarre());
        article.setQuantityTotal(addArticleDTO.getQuantityTotal());
        article.setCategory(category);
        return ResponseEntity.ok(articleRepository.save(article));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article articleDetails) {
        Optional<Article> articleOptional = articleRepository.findById(id);
        if (articleOptional.isPresent()) {
            Article article = articleOptional.get();
            article.setName(articleDetails.getName());
            article.setCodeBarre(articleDetails.getCodeBarre());
            article.setQuantityTotal(articleDetails.getQuantityTotal());
            article.setCategory(articleDetails.getCategory());
            Article updatedArticle = articleRepository.save(article);
            return ResponseEntity.ok(updatedArticle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        if (articleRepository.existsById(id)) {
            articleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/byCategory/{categoryId}")
    public List<Article> getArticlesByCategoryId(@PathVariable Long categoryId) {
        return articleRepository.findByCategoryId(categoryId);
    }
}
