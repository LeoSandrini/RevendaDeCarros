package com.studies.sandrini.revendadecarros;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class IOUtils {
    private static final String TAG = "IOUtils";

    /**
     * Converte a InputStream para String utilizando o charset informado
     *
     * @param in
     * @param charset UTF-8 ou ISO-8859-1
     * @return
     * @throws IOException
     */
    public static String toString(InputStream in, String charset) throws IOException {
        byte[] bytes = toBytes(in);
        String texto = new String(bytes, charset);
        return texto;
    }

    /**
     * Converte a InputStream para bytes[]
     *
     * @param in
     * @return
     */
    public static byte[] toBytes(InputStream in) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) > 0) {
                bos.write(buffer, 0, len);
            }
            byte[] bytes = bos.toByteArray();
            return bytes;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        } finally {
            try {
                bos.close();
                in.close();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
    }

    public static void writeString(OutputStream out, String string) {
        writeBytes(out, string.getBytes());
    }

    public static void writeBytes(OutputStream out, byte[] bytes) {
        try {
            out.write(bytes);
            out.flush();
            out.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    /**
     * Salva o texto em arquivo
     *
     * @param file
     * @param string
     */
    public static void writeString(File file, String string) {
        writeBytes(file, string.getBytes());
    }

    public static void writeBytes(File file, byte[] bytes) {
        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(bytes);
            out.flush();
            out.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public static String readString(File file) {
        try {
            if (file == null || !file.exists()) {
                return null;
            }
            InputStream in = new FileInputStream(file);
            String s = toString(in, "UTF-8");
            return s;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    /**
     * Salva a figura em aruqivo
     *
     * @param file
     * @param bitmap
     */
    public static void writeBitmap(File file, Bitmap bitmap) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public interface Callback {
        public void onFileSaved(File file, boolean exists);
    }


}
