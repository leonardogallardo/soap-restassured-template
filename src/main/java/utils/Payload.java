package utils;

import exception.InesperadoException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class Payload {

    private Payload() {
    }

    private static final Map<String, String> payloads = new ConcurrentHashMap<>();

    public static String get(final String name, Map<String, Object> inputs) {
        AtomicReference<String> contentRef = new AtomicReference<>(get(name));

        inputs.forEach((key, value) -> {
            if(value instanceof String) {
                contentRef.set(contentRef.get().replace("#(" + key + ")", value.toString()));
            } else {
                Map<String, String> subValues = (Map<String, String>) value;
                subValues.forEach((subKey, subValue) ->
                    contentRef.set(contentRef.get().replace("#(" + key + "." + subKey + ")", subValue))
                );
            }
        });

        return contentRef.get();
    }

    public static String get(final String name) {
        String content = payloads.get(name);

        if(content == null || content.isEmpty()) {
            try {
                InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(Contants.PAYLOADS + "/" + name);
                content = IOUtils.toString(inputStream, StandardCharsets.ISO_8859_1.toString());
            } catch (IOException e) {
                throw new InesperadoException(e);
            }

            payloads.put(name, content);
        }

        return content;
    }

}
