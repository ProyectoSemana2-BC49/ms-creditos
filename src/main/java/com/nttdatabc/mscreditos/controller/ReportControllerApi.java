package com.nttdatabc.mscreditos.controller;

import com.nttdatabc.mscreditos.api.ApiUtil;
import com.nttdatabc.mscreditos.model.BalanceAccounts;
import io.reactivex.rxjava3.core.Single;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import javax.annotation.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;



@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-01-29T09:50:18.294517600-05:00[America/Lima]")
@Validated
@Tag(name = "Reportes", description = "the Reportes API")
public interface ReportControllerApi {

  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  /**
   * GET /report/balance_credit/{customer_id} : Obtener resumen de saldos promedios del mes en curso de los productos crediticios.
   *
   * @param customerId ID del cliente (required)
   * @return Reporte de saldos promedios. (status code 200)
   *         or Error en request (status code 400)
   *         or Recurso no encontrado (status code 404)
   */
  @Operation(
      operationId = "getBalanceCredit",
      summary = "Obtener resumen de saldos promedios del mes en curso de los productos crediticios.",
      tags = { "Reportes" },
      responses = {
          @ApiResponse(responseCode = "200", description = "Reporte de saldos promedios.", content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = BalanceAccounts.class))
          }),
          @ApiResponse(responseCode = "400", description = "Error en request"),
          @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
      }
  )
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/report_credit/balance/{customer_id}",
      produces = { "application/json" }
  )
  default Single<ResponseEntity<BalanceAccounts>> getBalanceCredit(
      @Parameter(name = "customer_id", description = "ID del cliente", required = true, in = ParameterIn.PATH) @PathVariable("customer_id") String customerId
  ) {
    getRequest().ifPresent(request -> {
      for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
        if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
          String exampleString = "{ \"customerId\" : \"customerId\", \"summary_accounts\" : [ { \"account_id\" : \"account_id\", \"balanceAvg\" : 0.8008281904610115 }, { \"account_id\" : \"account_id\", \"balanceAvg\" : 0.8008281904610115 } ] }";
          ApiUtil.setExampleResponse(request, "application/json", exampleString);
          break;
        }
      }
    });
    return Single.just(new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED));

  }

}