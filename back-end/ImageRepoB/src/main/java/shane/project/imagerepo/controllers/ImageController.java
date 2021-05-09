package shane.project.imagerepo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shane.project.imagerepo.models.Image;
import shane.project.imagerepo.repository.ImageRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "image")
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

    @PostMapping("/upload")
    public ResponseEntity.BodyBuilder uplaodImage(
        @RequestParam("imageFile")
            MultipartFile file) throws IOException {
        System.out.println("Original Image Byte Size - " + file.getBytes().length);
        Image img = new Image(file.getOriginalFilename(),
            file.getContentType(),
            compressBytes(file.getBytes()));
        imageRepository.save(img);
        return ResponseEntity.status(HttpStatus.OK);
    }

    @GetMapping(path = {"/get/{imagename}"})
    public Image getImage(
        @PathVariable("imagename")
            String imagename) throws IOException {
        final Optional<Image> retrievedImage = imageRepository.findByImagename(imagename);
        Image img = new Image(retrievedImage.get()
            .getImagename(),
            retrievedImage.get()
                .getType(),
            decompressBytes(retrievedImage.get()
                .getPic()));
        return img;
    }

    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer,
                0,
                count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer,
                    0,
                    count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}