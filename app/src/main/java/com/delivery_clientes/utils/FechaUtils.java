package com.delivery_clientes.utils;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

public class FechaUtils {

    public static String obtenerFechaActual() {
        LocalDateTime ahora = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return ahora.format(formatter);
    }
}
