package com.example.cnewpoc.controller;

import com.example.cnewpoc.model.CNew;
import com.example.cnewpoc.model.SharePointAuth;
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
public class CNewController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/auth")
    public SharePointAuth auth() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // Set up the request body
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "client_credentials");
        requestBody.add("client_id", "b5175095-a87e-4500-a3d4-2dca69e168cc@f89c1178-4c5d-43b5-9f3b-d21c3bec61b5");
        requestBody.add("client_secret", "CjOx2ZN9816DxXoYoN/CzAUWjkkIsM/ju3v0eSpAyfc=");
        requestBody.add("resource", "00000003-0000-0ff1-ce00-000000000000/cmcglobalcompany.sharepoint.com@f89c1178-4c5d-43b5-9f3b-d21c3bec61b5");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        String url = "https://accounts.accesscontrol.windows.net/f89c1178-4c5d-43b5-9f3b-d21c3bec61b5/tokens/oAuth/2";

        return restTemplate
                .postForObject(url, requestEntity, SharePointAuth.class);
    }

    @GetMapping("/list")
    public CNew getNewsList(@RequestParam String token,
                              @RequestParam(required = false, defaultValue = "") String keyWord) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json;odata=verbose");
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        String filter = String.format("&$filter=substringof('%s',Title)", keyWord);

        String url = "https://cmcglobalcompany.sharepoint.com/sites/news48/_api/web/GetFolderByServerRelativeUrl('SitePages')" +
                "/Files?$select=Title,Author/Title,ListItemAllFields/BannerImageUrl,ListItemAllFields/Description,TimeCreated&$expand=ListItemAllFields,Author" + filter;

        return restTemplate.exchange(url, HttpMethod.GET, entity, CNew.class).getBody();
    }
}
