package eya.gestiondesstock.portail.controller;

import eya.gestiondesstock.portail.entity.StockHistory;
import eya.gestiondesstock.portail.repository.StockHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stock-history")
public class StockHistoryController {

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