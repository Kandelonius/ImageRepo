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

    @Column(name = "type")
    private String type;

    @Column(name="pic")
    private byte[] pic;

    public Image() {
    }

    public Image(
        String imagename,
        String type,
        byte[] pic) {
        this.imagename = imagename;
        this.type = type;
        this.pic = pic;
    }

    public long getImageid() {
        return imageid;
    }

    public void setImageid(long imageid) {
        this.imageid = imageid;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }
}
