package com.vaccinetracker.query.controller;

import com.vaccinetracker.query.model.QueryServiceResponseModel;
import com.vaccinetracker.query.service.QueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/vaccine")
public class QueryServiceController {

    private final QueryService queryService;

    public QueryServiceController(QueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping("/booking/{id}")
    public ResponseEntity<QueryServiceResponseModel> getBookingById(@PathVariable("id") final String id) {
        QueryServiceResponseModel queryServiceResponseModel = queryService.getBookingById(id);
        return ResponseEntity.ok(queryServiceResponseModel);
    }

    @GetMapping("/booking/{govId}/history")
    public ResponseEntity<List<QueryServiceResponseModel>> getBookingHistoryByGovId(
            @PathVariable("govId") final String govId) {
        List<QueryServiceResponseModel> queryServiceResponseModels = queryService.getBookingHistoryByGovId(govId);
        return ResponseEntity.ok(queryServiceResponseModels);
    }

    @GetMapping("/booking/{govId}/{status}")
    public ResponseEntity<List<QueryServiceResponseModel>> getBookingByGovIdAndStatus(
            @PathVariable("govId") final String govId, @PathVariable("status") final String status) {
        List<QueryServiceResponseModel> queryServiceResponseModels =
                queryService.getBookingByGovIdAndStatus(govId, status);
        return ResponseEntity.ok(queryServiceResponseModels);
    }

    @GetMapping("/booking/search")
    public ResponseEntity<List<QueryServiceResponseModel>> searchByText(
            @RequestParam("text") final String text) {
        List<QueryServiceResponseModel> queryServiceResponseModels = queryService.searchByText(text);
        return ResponseEntity.ok(queryServiceResponseModels);
    }
}
