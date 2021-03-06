package org.schicwp.dinky.api;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.schicwp.dinky.content.ContentService;
import org.schicwp.dinky.content.PermissionService;
import org.schicwp.dinky.exceptions.PermissionException;
import org.schicwp.dinky.model.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Created by will.schick on 1/14/19.
 */
@RestController
@RequestMapping("/api/v1/assets")
public class AssetResource {

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    PermissionService permissionService;

    @Autowired
    ContentService contentService;

    @GetMapping("{id}")
    void getAsset(@PathVariable("id") String id, HttpServletResponse response) throws IOException {

        GridFSFile file = gridFsTemplate
                .findOne(new Query(Criteria.where("_id").is(id)));

        Content content = contentService
                .findById(file.getMetadata().getString("contentId"))
                .orElseThrow(NoSuchElementException::new);

        if (!permissionService.allowRead(content))
            throw new PermissionException();

        response.addHeader(
                "Content-Type",
                file.getMetadata().getString("contentType")
        );

        InputStream out =  gridFsTemplate
                .getResource(Objects.requireNonNull(file).getFilename())
                .getInputStream();

        byte[] buffer = new byte[1024];

        int read;

        while ((read = out.read(buffer)) >= 0)
            response.getOutputStream().write(buffer,0,read);

    }


}
