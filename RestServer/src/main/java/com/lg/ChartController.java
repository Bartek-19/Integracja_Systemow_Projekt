package com.lg;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/api/charts")
public class ChartController {

    private final Path chartDir = Paths.get("").toAbsolutePath(); // folder RestServer

    @GetMapping
    public ResponseEntity<List<String>> listChartFiles() {
        try {
            List<String> pngFiles = new ArrayList<>();

            DirectoryStream<Path> stream = Files.newDirectoryStream(chartDir, "*.png");
            for (Path path : stream) {
                pngFiles.add(path.getFileName().toString());
            }

            return ResponseEntity.ok(pngFiles);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> getChart(@PathVariable String fileName) {
        try {
            Path filePath = chartDir.resolve(fileName);
            if (!Files.exists(filePath) || !fileName.endsWith(".png")) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(filePath.toUri());
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
