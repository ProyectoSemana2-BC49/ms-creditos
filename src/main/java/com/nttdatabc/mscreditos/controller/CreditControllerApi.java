package com.nttdatabc.mscreditos.controller;

import com.nttdatabc.mscreditos.api.ApiUtil;
import com.nttdatabc.mscreditos.model.Credit;
import com.nttdatabc.mscreditos.utils.exceptions.errors.ErrorResponseException;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;


@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-01-26T11:53:42.766051900-05:00[America/Lima]")
@Validated
@Tag(name = "Créditos", description = "the Créditos API")
public interface CreditControllerApi {

  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  /**
   * POST /credits : Insertar un nuevo crédito.
   *
   * @param credit (required).
   * @return Crédito insertado exitosamente (status code 201).
   * or Error en request (status code 400).
   */
  @Operation(
      operationId = "createCredit",
      summary = "Insertar un nuevo crédito",
      tags = {"Créditos"},
      responses = {
          @ApiResponse(responseCode = "201", description = "Crédito insertado exitosamente"),
          @ApiResponse(responseCode = "400", description = "Error en request")
      }
  )
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/credits",
      consumes = {"application/json"}
  )
  default Maybe<ResponseEntity<Object>> createCredit(
      @Parameter(name = "Credit", description = "", required = true) @Valid @RequestBody Credit credit
  ) throws ErrorResponseException {
    return Maybe.just(new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED));

  }


  /**
   * DELETE /credits/{credit_id} : Eliminar un crédito por ID.
   *
   * @param creditId (required).
   * @return Crédito eliminado exitosamente (status code 200).
   * or Error en Request (status code 400).
   * or Recurso no encontrado (status code 404).
   */
  @Operation(
      operationId = "deleteCreditById",
      summary = "Eliminar un crédito por ID",
      tags = {"Créditos"},
      responses = {
          @ApiResponse(responseCode = "200", description = "Crédito eliminado exitosamente"),
          @ApiResponse(responseCode = "400", description = "Error en Request"),
          @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
      }
  )
  @RequestMapping(
      method = RequestMethod.DELETE,
      value = "/credits/{credit_id}"
  )
  default Maybe<ResponseEntity<Object>> deleteCreditById(
      @Parameter(name = "credit_id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("credit_id") String creditId
  ) throws ErrorResponseException {
    return Maybe.just(new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED));

  }


  /**
   * GET /credits : Obtener todos los créditos.
   *
   * @return Lista de créditos obtenida exitosamente (status code 200).
   */
  @Operation(
      operationId = "getAllCredits",
      summary = "Obtener todos los créditos",
      tags = {"Créditos"},
      responses = {
          @ApiResponse(responseCode = "200", description = "Lista de créditos obtenida exitosamente", content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Credit.class)))
          })
      }
  )
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/credits",
      produces = {"application/json"}
  )
  default Observable<ResponseEntity<List<Credit>>> getAllCredits(

  ) {
    getRequest().ifPresent(request -> {
      for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
        if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
          String exampleString = "[ { \"date_open\" : \"date_open\", \"mount_limit\" : 0.8008281904610115, \"interest_rate\" : 6.027456183070403, \"type_credit\" : \"type_credit\", \"_id\" : \"_id\", \"customer_id\" : \"customer_id\" }, { \"date_open\" : \"date_open\", \"mount_limit\" : 0.8008281904610115, \"interest_rate\" : 6.027456183070403, \"type_credit\" : \"type_credit\", \"_id\" : \"_id\", \"customer_id\" : \"customer_id\" } ]";
          ApiUtil.setExampleResponse(request, "application/json", exampleString);
          break;
        }
      }
    });
    return Observable.just(new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED));

  }


  /**
   * GET /credits/{credit_id} : Trae información de un crédito según su credit_id.
   *
   * @param creditId (required).
   * @return Crédito obtenido correctamente (status code 200).
   * or Error en Request (status code 400).
   * or Recurso no encontrado (status code 404).
   */
  @Operation(
      operationId = "getCreditById",
      summary = "Trae información de un crédito según su credit_id",
      tags = {"Créditos"},
      responses = {
          @ApiResponse(responseCode = "200", description = "Crédito obtenido correctamente", content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = Credit.class))
          }),
          @ApiResponse(responseCode = "400", description = "Error en Request"),
          @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
      }
  )
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/credits/{credit_id}",
      produces = {"application/json"}
  )
  default Single<ResponseEntity<Credit>> getCreditById(
      @Parameter(name = "credit_id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("credit_id") String creditId
  ) throws ErrorResponseException {
    getRequest().ifPresent(request -> {
      for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
        if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
          String exampleString = "{ \"date_open\" : \"date_open\", \"mount_limit\" : 0.8008281904610115, \"interest_rate\" : 6.027456183070403, \"type_credit\" : \"type_credit\", \"_id\" : \"_id\", \"customer_id\" : \"customer_id\" }";
          ApiUtil.setExampleResponse(request, "application/json", exampleString);
          break;
        }
      }
    });
    return Single.just(new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED));

  }


  /**
   * GET /credits/customer/{customer_id} : Obtener los créditos por ID de cliente.
   *
   * @param customerId (required).
   * @return Lista de créditos obtenida exitosamente (status code 200).
   */
  @Operation(
      operationId = "getCreditsByCustomerId",
      summary = "Obtener los créditos por ID de cliente",
      tags = {"Créditos"},
      responses = {
          @ApiResponse(responseCode = "200", description = "Lista de créditos obtenida exitosamente", content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Credit.class)))
          })
      }
  )
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/credits/customer/{customer_id}",
      produces = {"application/json"}
  )
  default Observable<ResponseEntity<List<Credit>>> getCreditsByCustomerId(
      @Parameter(name = "customer_id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("customer_id") String customerId
  ) throws ErrorResponseException {
    getRequest().ifPresent(request -> {
      for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
        if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
          String exampleString = "[ { \"date_open\" : \"date_open\", \"mount_limit\" : 0.8008281904610115, \"interest_rate\" : 6.027456183070403, \"type_credit\" : \"type_credit\", \"_id\" : \"_id\", \"customer_id\" : \"customer_id\" }, { \"date_open\" : \"date_open\", \"mount_limit\" : 0.8008281904610115, \"interest_rate\" : 6.027456183070403, \"type_credit\" : \"type_credit\", \"_id\" : \"_id\", \"customer_id\" : \"customer_id\" } ]";
          ApiUtil.setExampleResponse(request, "application/json", exampleString);
          break;
        }
      }
    });
    return Observable.just(new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED));

  }


  /**
   * PUT /credits : Actualizar un crédito existente.
   *
   * @param credit (required).
   * @return Actualizado correctamente (status code 200).
   * or Error en el request (status code 400).
   * or Recurso no encontrado (status code 404).
   */
  @Operation(
      operationId = "updateCredit",
      summary = "Actualizar un crédito existente",
      tags = {"Créditos"},
      responses = {
          @ApiResponse(responseCode = "200", description = "Actualizado correctamente"),
          @ApiResponse(responseCode = "400", description = "Error en el request"),
          @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
      }
  )
  @RequestMapping(
      method = RequestMethod.PUT,
      value = "/credits",
      consumes = {"application/json"}
  )
  default Maybe<ResponseEntity<Object>> updateCredit(
      @Parameter(name = "Credit", description = "", required = true) @Valid @RequestBody Credit credit
  ) throws ErrorResponseException {
    return Maybe.just(new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED));

  }

}
