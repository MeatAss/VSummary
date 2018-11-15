package Com.VSummary.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.InputStream;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class MultipartFileBuffer {
    private String id;
    private String name;
    private InputStream inputStream;

    public MultipartFileBuffer(String id, String name, InputStream inputStream) {
        this.name = UUID.randomUUID().toString() + name;
        this.inputStream = inputStream;
        this.id = id;
    }
}
