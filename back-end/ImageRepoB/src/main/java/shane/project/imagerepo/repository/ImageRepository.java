package shane.project.imagerepo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shane.project.imagerepo.models.Image;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByImagename(String imagename);
}
