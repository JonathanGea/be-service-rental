package com.gea.app.shared.util;

//import io.supabase.client.SupabaseClient;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
public class SupabaseStorageUploader {

//    private final SupabaseClient supabaseClient;
//
//    public SupabaseStorageUploader(SupabaseClient supabaseClient) {
//        this.supabaseClient = supabaseClient;
//    }

    public String uploadFile(String bucketName, MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        try {
//            supabaseClient.storage()
//                    .from(bucketName)
//                    .upload(fileName, file.getBytes());
//
//            return supabaseClient.storage()
//                    .from(bucketName)
//                    .getPublicUrl(fileName);
            return "https://fastly.picsum.photos/id/87/1280/960.jpg?hmac=tyU21LuCEO1qRepY4GnT9gGkfKbvY__ZrZYg_JxZxI8";
        } catch (Exception e) {
            throw new IOException("Failed to upload to Supabase Storage", e);
        }
    }
}