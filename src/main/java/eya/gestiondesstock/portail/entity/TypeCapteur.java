package eya.gestiondesstock.portail.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeCapteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String reference ;

    private String modele ;

    @OneToMany(mappedBy = "typeCapteur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Capteur> capteurs;
}
