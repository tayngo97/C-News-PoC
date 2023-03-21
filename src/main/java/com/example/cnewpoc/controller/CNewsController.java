package com.example.cnewpoc.controller;

import com.example.cnewpoc.model.*;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/api")
public class CNewsController {

    private static final String SHAREPOINT_API_URL = "https://cmcglobalcompany.sharepoint.com";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/list")
    public Table getNewsList(@RequestParam(required = false) String searchKey,
                             @RequestParam(required = false, defaultValue = "false") Boolean isMostRead,
                             @RequestParam(required = false, defaultValue = "10") String rowLimit,
                             @ApiParam(value = "Category", allowableValues = "Tin tức, Sự kiện, Về CMC Global")
                             @RequestParam(required = false, defaultValue = "Tin tức") String category) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json;odata=verbose");
        headers.set("Authorization", "Bearer " + getAccessToken());

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        String getNewsListUrl = buildNewsListUrl(searchKey,isMostRead,rowLimit,category);

        CNews cNew = restTemplate.exchange(getNewsListUrl, HttpMethod.GET, entity, CNews.class).getBody();
        assert cNew != null;
        return cNew.d.query.primaryQueryResult.relevantResults.table;
    }

    private String buildNewsListUrl(String searchKey, Boolean isMostRead, String rowLimit, String category) {
        String sortString = isMostRead ? "&sortlist='ViewsLifeTime:descending'" : " ";
        String searchString = searchKey != null ? String.format("AND Title:\"%s\" ", searchKey) : "";
        return SHAREPOINT_API_URL + "/_api/search/query?" +
                "querytext='Path:\"" + SHAREPOINT_API_URL + "/sites/news48/SitePages/*\"" + searchString + "'&selectproperties='Author,Title,PictureThumbnailURL,OriginalPath," +
                "IdentityWebId,Description,ViewsLifeTime,LikeCount,CommentCount,Created,Write'&rowlimit=" + rowLimit + sortString;
    }

    @GetMapping("/details")
    public ListItemAllFields getNewsDetail(@RequestParam String uniqueId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json;odata=verbose");
        headers.set("Authorization", "Bearer " + getAccessToken());

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        String url = "https://cmcglobalcompany.sharepoint.com/sites/news48/_api/web/GetFolderByServerRelativeUrl('SitePages')/Files?$select=UniqueId,ListItemAllFields/CanvasContent1&$expand=ListItemAllFields";

        CNewsDetail cNew = restTemplate.exchange(url, HttpMethod.GET, entity, CNewsDetail.class).getBody();
        List<Result> results = cNew.d.results;
        ListItemAllFields listItemAllFields = findListItemByUniqueId(results, uniqueId);
        String sanitizedCanvasContent = sanitizeCanvasContent(listItemAllFields.getCanvasContent1());
        String htmlString = generateHtmlString(sanitizedCanvasContent);
        listItemAllFields.setCanvasContent1(htmlString);
        return listItemAllFields;
    }

    private ListItemAllFields findListItemByUniqueId(List<Result> results, String uniqueId) {
        return results.stream()
                .filter(d -> d.uniqueId.equals(uniqueId))
                .findFirst()
                .get()
                .getListItemAllFields();
    }

    private String sanitizeCanvasContent(String canvasContent) {
        return canvasContent.replace("\"", "").replace("src=", "src=https://cmcglobalcompany.sharepoint.com");
    }

    private String generateHtmlString(String canvasContent) {
        return String.format("<!DOCTYPE html><html lang=en><head><meta charset=UTF-8><title>Title</title></head><body>%s</body></html>", canvasContent);
    }

    public String getAccessToken() {

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

        return Objects.requireNonNull(restTemplate
                .postForObject(url, requestEntity, SharePointAuth.class)).getAccess_token();
    }

}
