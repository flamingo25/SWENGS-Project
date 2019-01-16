package swengs.therapiedb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import swengs.therapiedb.model.animal.AnimalImage;
import swengs.therapiedb.service.AnimalImageService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/dto/animals/image")
public class AnimalImageController {

    @Autowired
    private AnimalImageService animalImageService;

    // ---------------------------------------------------------------------------------
    @PostMapping("{id}")
    public ResponseEntity<AnimalImage> uploadMedia(@PathVariable Long id, @RequestPart MultipartFile file, UriComponentsBuilder ucBuilder) throws IOException, URISyntaxException {
    // ---------------------------------------------------------------------------------

        AnimalImage result = animalImageService.createMedia(file, id);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/animals/image/{id}").buildAndExpand(result.getId()).toUri());
        return new ResponseEntity<AnimalImage>(headers, HttpStatus.CREATED);
    }

    // ---------------------------------------------------------------------------------
    @GetMapping("{id}")
    public ResponseEntity<InputStreamResource> getMediaDownload(@PathVariable Long id) throws FileNotFoundException {
    // ---------------------------------------------------------------------------------

        Optional<AnimalImage> mediaResult = animalImageService.findOne(id);
        if (!mediaResult.isPresent()) {
            return new ResponseEntity<InputStreamResource>(HttpStatus.NOT_FOUND);
        }
        AnimalImage media = mediaResult.get();
        File mediaFile = animalImageService.retrieveMediaFile(media);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(mediaFile));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + media.getOriginalFileName());
        headers.setContentType(MediaType.parseMediaType(media.getContentType()));
        headers.setContentLength(media.getSize());
        return new ResponseEntity<InputStreamResource>(resource ,headers, HttpStatus.OK);
    }
}
