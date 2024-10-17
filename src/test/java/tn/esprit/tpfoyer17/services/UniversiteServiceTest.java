package tn.esprit.tpfoyer17.services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
class UniversiteServiceTest {

    @Autowired
    IUniversiteService universiteService;

    @Autowired
    EntityManager entityManager;

    private Universite testUniversite;

    @BeforeEach
    void setUp() {
        Foyer foyer = Foyer.builder()
                .nomFoyer("Ras Tabia")
                .capaciteFoyer(200)
                .build();

        // Persist the foyer before associating it with the Universite
        entityManager.persist(foyer);

        testUniversite = Universite.builder()
                .nomUniversite("FST")
                .adresse("Manar, Tunis")
                .foyer(foyer)
                .build();
    }

    @Test
    @Rollback
    void addUniversite() {
        Universite addedUniversite = universiteService.addUniversite(testUniversite);
        assertNotNull(addedUniversite.getIdUniversite());
        assertEquals("FST", addedUniversite.getNomUniversite());
        assertEquals("Manar, Tunis", addedUniversite.getAdresse());
    }

    @Test
    void getAllUniversitesWhenEmpty() {
        List<Universite> universites = universiteService.getAllUniversites();
        assertTrue(universites.isEmpty());
    }

    @Test
    @Rollback
    void getAllUniversitesWithOneAddedUniversite() {
        universiteService.addUniversite(testUniversite);
        List<Universite> universites = universiteService.getAllUniversites();
        assertFalse(universites.isEmpty());
        assertEquals(1, universites.size());
    }

    @Test
    void getUniversiteById() {
        Universite addedUniversite = universiteService.addUniversite(testUniversite);
        Universite foundUniversite = universiteService.getUniversiteById(addedUniversite.getIdUniversite());
        assertNotNull(foundUniversite);
        assertEquals(addedUniversite.getIdUniversite(), foundUniversite.getIdUniversite());
    }

    @Test
    @Rollback
    void deleteUniversite() {
        Universite addedUniversite = universiteService.addUniversite(testUniversite);

        universiteService.deleteUniversite(addedUniversite.getIdUniversite());

       Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            universiteService.getUniversiteById(addedUniversite.getIdUniversite());
        });

        String expectedMessage = "Universite not found with id: " + addedUniversite.getIdUniversite();
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    @Rollback
    void updateUniversite() {
        Universite addedUniversite = universiteService.addUniversite(testUniversite);
        addedUniversite.setNomUniversite("FST Updated");
        addedUniversite.setAdresse("Manar, Tunis Updated");
        Universite updatedUniversite = universiteService.updateUniversite(addedUniversite);
        assertEquals("FST Updated", updatedUniversite.getNomUniversite());
        assertEquals("Manar, Tunis Updated", updatedUniversite.getAdresse());
    }
}