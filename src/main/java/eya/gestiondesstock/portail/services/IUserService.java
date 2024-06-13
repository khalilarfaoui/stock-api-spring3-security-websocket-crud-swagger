package eya.gestiondesstock.portail.services;

import eya.gestiondesstock.portail.entity.Utilisateur;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface IUserService {
    List<Utilisateur> getAllUsers();
    Utilisateur createUser(Utilisateur user);
    Utilisateur updateUser(Utilisateur user);
    void deleteUser(Long id);
    Utilisateur findById(Long id);
    UserDetails loadUserByUsername(String username);
}
