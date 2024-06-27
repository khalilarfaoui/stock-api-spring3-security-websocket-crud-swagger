package eya.gestiondesstock.portail.controller;

import eya.gestiondesstock.portail.entity.Stock;
import eya.gestiondesstock.portail.entity.StockHistory;
import eya.gestiondesstock.portail.entity.Utilisateur;
import eya.gestiondesstock.portail.entity.dto.StockHistoryPullDTO;
import eya.gestiondesstock.portail.repository.StockHistoryRepository;
import eya.gestiondesstock.portail.repository.StockRepository;
import eya.gestiondesstock.portail.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/stock-history")
public class StockHistoryController {
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StockHistoryRepository stockHistoryRepository;

    @GetMapping
    public List<StockHistory> getAllStockHistories() {
        return stockHistoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockHistory> getStockHistoryById(@PathVariable Long id) {
        Optional<StockHistory> stockHistory = stockHistoryRepository.findById(id);
        return stockHistory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public StockHistory createStockHistory(@RequestBody StockHistory stockHistory) {
        return stockHistoryRepository.save(stockHistory);
    }

    @PostMapping("pull")
    public ResponseEntity<?> pullStock(@RequestBody StockHistoryPullDTO stockHistoryPullDTO) {
        Optional<Utilisateur> utilisateur = userRepository.findById(stockHistoryPullDTO.getPullBy());
        Optional<Stock> stock = stockRepository.findById(stockHistoryPullDTO.getStock());
        if(utilisateur.isPresent() && stock.isPresent()){
            StockHistory stockHistory = new StockHistory();
            stockHistory.setStock(stock.get());
            stockHistory.setPull(stockHistoryPullDTO.getPull());
            stockHistory.setPullBy(utilisateur.get());
            stockHistory.setLocalDateTime(LocalDateTime.now());
            return ResponseEntity.ok(stockHistoryRepository.save(stockHistory));
        }else{
          return ResponseEntity.badRequest().body("Bad Request");
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<StockHistory> updateStockHistory(@PathVariable Long id, @RequestBody StockHistory stockHistoryDetails) {
        Optional<StockHistory> stockHistoryOptional = stockHistoryRepository.findById(id);

        if (stockHistoryOptional.isPresent()) {
            StockHistory stockHistory = stockHistoryOptional.get();
            stockHistory.setLocalDateTime(stockHistoryDetails.getLocalDateTime());
            stockHistory.setStock(stockHistoryDetails.getStock());
            stockHistory.setPull(stockHistoryDetails.getPull());
            stockHistory.setPut(stockHistoryDetails.getPut());
            stockHistory.setAddedBy(stockHistoryDetails.getAddedBy());
            stockHistory.setPullBy(stockHistoryDetails.getPullBy());

            StockHistory updatedStockHistory = stockHistoryRepository.save(stockHistory);
            return ResponseEntity.ok(updatedStockHistory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockHistory(@PathVariable Long id) {
        if (stockHistoryRepository.findById(id).isPresent()) {
            stockHistoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}