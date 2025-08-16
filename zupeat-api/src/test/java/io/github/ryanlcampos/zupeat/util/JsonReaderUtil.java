package io.github.ryanlcampos.zupeat.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonReaderUtil {
    
    public static String obterConteudo(String resourcePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(resourcePath)));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo JSON: " + resourcePath, e);
        }
    }

}
