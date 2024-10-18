package tn.esprit.tpfoyer17.services;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UniversiteServiceTest {

    @Mock
    IUniversiteService universiteService;  // Mock the service

    @Mock
    UniversiteRepository universiteRepository;  // Mock the repository

    @InjectMocks
    UniversiteService universiteServiceImpl;  // Inject mocks into the service implementation

    private Universite testUniversite;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks

        Foyer foyer = Foyer.builder()
                .nomFoyer("Ras Tabia")
                .capaciteFoyer(200)
                .build();

        testUniversite = Universite.builder()
                .nomUniversite("FST")
                .adresse("Manar, Tunis")
                .foyer(foyer)
                .build();
    }

    @Test
    void addUniversite() {
        when(universiteService.addUniversite(any(Universite.class))).thenReturn(testUniversite);

        Universite addedUniversite = universiteService.addUniversite(testUniversite);
        assertNotNull(addedUniversite);
        assertEquals("FST", addedUniversite.getNomUniversite());
        verify(universiteService, times(1)).addUniversite(testUniversite);  // Verify that the method was called once
    }

    @Test
    void getUniversiteById() {
        when(universiteService.getUniversiteById(anyLong())).thenReturn(testUniversite);

        Universite foundUniversite = universiteService.getUniversiteById(1L);
        assertNotNull(foundUniversite);
        assertEquals("FST", foundUniversite.getNomUniversite());
        verify(universiteService, times(1)).getUniversiteById(1L);
    }

    @Test
    void deleteUniversite() {
        doNothing().when(universiteService).deleteUniversite(anyLong());

        universiteService.deleteUniversite(1L);
        verify(universiteService, times(1)).deleteUniversite(1L);
    }

    @Test
    void getUniversiteByIdThrowsException() {
        when(universiteService.getUniversiteById(1L)).thenThrow(new EntityNotFoundException("Universite not found with id: 1"));

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            universiteService.getUniversiteById(1L);
        });

        String expectedMessage = "Universite not found with id: 1";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
