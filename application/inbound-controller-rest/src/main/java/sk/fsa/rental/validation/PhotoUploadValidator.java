package sk.fsa.rental.validation;

import org.springframework.web.multipart.MultipartFile;
import sk.fsa.rental.domain.RentalException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public final class PhotoUploadValidator {

    private static final long MAX_PHOTO_SIZE_BYTES = 10L * 1024L * 1024L;
    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/webp");

    private PhotoUploadValidator() {
    }

    public static void validate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RentalException(RentalException.Type.VALIDATION, "Photo file is required.", "file");
        }
        if (file.getSize() > MAX_PHOTO_SIZE_BYTES) {
            throw new RentalException(RentalException.Type.VALIDATION, "Photo must be 10 MB or smaller.", "file");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase())) {
            throw new RentalException(RentalException.Type.VALIDATION, "Only JPEG, PNG, GIF or WebP images can be uploaded.", "file");
        }
        if (!hasMatchingMagicBytes(file, contentType.toLowerCase())) {
            throw new RentalException(RentalException.Type.VALIDATION, "Uploaded file content does not match an allowed image type.", "file");
        }
    }

    private static boolean hasMatchingMagicBytes(MultipartFile file, String contentType) {
        byte[] header = new byte[12];
        int read;
        try (InputStream input = file.getInputStream()) {
            read = input.read(header);
        } catch (IOException ex) {
            throw new RentalException(RentalException.Type.VALIDATION, "Unable to read uploaded photo.", "file");
        }

        return switch (contentType) {
            case "image/jpeg" -> read >= 3
                    && unsigned(header[0]) == 0xFF
                    && unsigned(header[1]) == 0xD8
                    && unsigned(header[2]) == 0xFF;
            case "image/png" -> read >= 8
                    && unsigned(header[0]) == 0x89
                    && header[1] == 'P'
                    && header[2] == 'N'
                    && header[3] == 'G'
                    && unsigned(header[4]) == 0x0D
                    && unsigned(header[5]) == 0x0A
                    && unsigned(header[6]) == 0x1A
                    && unsigned(header[7]) == 0x0A;
            case "image/gif" -> read >= 6
                    && header[0] == 'G'
                    && header[1] == 'I'
                    && header[2] == 'F'
                    && header[3] == '8'
                    && (header[4] == '7' || header[4] == '9')
                    && header[5] == 'a';
            case "image/webp" -> read >= 12
                    && header[0] == 'R'
                    && header[1] == 'I'
                    && header[2] == 'F'
                    && header[3] == 'F'
                    && header[8] == 'W'
                    && header[9] == 'E'
                    && header[10] == 'B'
                    && header[11] == 'P';
            default -> false;
        };
    }

    private static int unsigned(byte value) {
        return value & 0xFF;
    }
}
