package com.example.cnewpoc.controller;

import com.example.cnewpoc.model.CNew;
import com.example.cnewpoc.model.SharePointAuth;
import com.example.cnewpoc.model.Table;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api")
public class CNewsController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/auth")
    public SharePointAuth auth() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // Set up the request body
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "client_credentials");
        requestBody.add("client_id", "3c2ff393-bea2-471b-9c1a-573cde5cfc0e@f89c1178-4c5d-43b5-9f3b-d21c3bec61b5");
        requestBody.add("client_secret", "SOFNz6CDAHJ96a0dh+puE0WQNN/QUVInJuLVLcG6ihU=");
        requestBody.add("resource", "00000003-0000-0ff1-ce00-000000000000/cmcglobalcompany.sharepoint.com@f89c1178-4c5d-43b5-9f3b-d21c3bec61b5");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        String url = "https://accounts.accesscontrol.windows.net/f89c1178-4c5d-43b5-9f3b-d21c3bec61b5/tokens/oAuth/2";

        return restTemplate
                .postForObject(url, requestEntity, SharePointAuth.class);
    }

    @GetMapping("/list")
    public Table getNewsList(@RequestParam String token,
                             @RequestParam(required = false) String searchKey,
                             @RequestParam(required = false, defaultValue = "false") Boolean isMostRead,
                             @RequestParam(required = false, defaultValue = "10") String rowLimit,
                             @ApiParam(value = "Category", allowableValues = "Tin tức, Sự kiện, Về CMC Global")
                             @RequestParam(required = false, defaultValue = "Tin tức") String category) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json;odata=verbose");
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        String sortString = isMostRead ? "&sortlist='ViewsLifeTime:descending'" : " ";
        String searchString = searchKey != null ? String.format("AND Title:\"%s\" ", searchKey) : "";

        String url = "https://cmcglobalcompany.sharepoint.com/_api/search/query?" +
                "querytext='Path:\"https://cmcglobalcompany.sharepoint.com/sites/news48/SitePages/*\"%s'&selectproperties='Author,Title,PictureThumbnailURL,OriginalPath," +
                "IdentityWebId,Description,ViewsLifeTime,LikeCount,CommentCount,Created,Write'&rowlimit=" + rowLimit + sortString;

        url = String.format(url, searchString);
        CNew cNew = restTemplate.exchange(url, HttpMethod.GET, entity, CNew.class).getBody();
        assert cNew != null;
        return cNew.d.query.primaryQueryResult.relevantResults.table;
    }

}
