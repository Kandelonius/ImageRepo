package shane.project.imagerepo.models;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long imageid;

    @Column(nullable = false,
        unique = true)
    private String imagename;
}
