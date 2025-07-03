package pl.hszaba.betacomtask.item.ports;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.hszaba.betacomtask.common.security.Operator;
import pl.hszaba.betacomtask.item.ItemCreateRequest;
import pl.hszaba.betacomtask.item.ItemModel;
import pl.hszaba.betacomtask.item.domain.ItemService;

import java.util.List;

@RestController
@RequestMapping("/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;


    @Operation(summary = "Create a new item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item created successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid or missing token")
    })
    @PostMapping
    public ResponseEntity<Void> createItem(
            @AuthenticationPrincipal Operator operator,
            @RequestBody @Valid ItemCreateRequest itemCreateRequest) {
        itemService.createItem(itemCreateRequest, operator);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get current user's items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of items"),
            @ApiResponse(responseCode = "401", description = "Invalid or missing token")
    })
    @GetMapping
    public ResponseEntity<List<ItemModel>> getCurrentUserItems(
            @AuthenticationPrincipal Operator operator)
    {
        return ResponseEntity.ok(itemService.getCurrentUserItems(operator));
    }
}
