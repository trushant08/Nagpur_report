/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.model;

import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author shrutika
 */
public class CsvFileUpload implements Serializable {

    private MultipartFile multipartFile;

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
