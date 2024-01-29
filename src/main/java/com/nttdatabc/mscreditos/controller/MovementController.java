package com.nttdatabc.mscreditos.controller;

import static com.nttdatabc.mscreditos.utils.Constantes.PREFIX_PATH;

import com.nttdatabc.mscreditos.model.MovementCredit;
import com.nttdatabc.mscreditos.model.PaidInstallment;
import com.nttdatabc.mscreditos.service.MovementServiceImpl;
import com.nttdatabc.mscreditos.utils.exceptions.errors.ErrorResponseException;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller de movements.
 */
@RestController
@RequestMapping(PREFIX_PATH)
@Slf4j
public class MovementController implements MovementControllerApi {

  @Autowired
  private MovementServiceImpl movementServiceImpl;

  @Override
  public Maybe<ResponseEntity<Object>> createMovementCredit(MovementCredit movementCredit) throws ErrorResponseException {
    return movementServiceImpl.createMovementCreditService(movementCredit)
        .subscribeOn(Schedulers.io())
        .doOnSubscribe(disposable -> log.debug("createMovementCredit:: init"))
        .andThen(Maybe.just(ResponseEntity.status(HttpStatus.CREATED).build()))
        .doOnSuccess(response -> log.info("createMovementCredit:: completed"));
  }

  @Override
  public Observable<ResponseEntity<List<MovementCredit>>> getMovementsCreditsByCreditId(String creditId) throws ErrorResponseException {
    return movementServiceImpl.getMovementsCreditsByCreditIdService(creditId)
        .subscribeOn(Schedulers.io())
        .doOnSubscribe(disposable -> log.info("getMovementsCreditsByCreditId:: init"))
        .doOnComplete(() -> log.info("getMovementsCreditsByCreditId:: completed"))
        .map(authorizedSigners -> ResponseEntity.ok().body(authorizedSigners));
  }

  @Override
  public Single<ResponseEntity<MovementCredit>> getMovementCreditById(String movementId) throws ErrorResponseException {
    return movementServiceImpl.getMovementCreditByIdService(movementId)
        .subscribeOn(Schedulers.io())
        .doOnSubscribe(disposable -> log.debug("getMovementCreditById:: init"))
        .map(ResponseEntity::ok)
        .doOnSuccess(cus -> log.debug("getMovementCreditById:: completed"));
  }

  @Override
  public Maybe<ResponseEntity<Object>> createPaymentInstallmentByMovementId(String movementId, PaidInstallment paidInstallment) throws ErrorResponseException {
    return movementServiceImpl.createPaymentInstallmentByMovementId(movementId, paidInstallment)
        .subscribeOn(Schedulers.io())
        .doOnSubscribe(disposable -> log.debug("createPaymentInstallmentByMovementId:: init"))
        .andThen(Maybe.just(ResponseEntity.status(HttpStatus.CREATED).build()))
        .doOnSuccess(response -> log.info("createPaymentInstallmentByMovementId:: completed"));
  }

  @Override
  public Maybe<ResponseEntity<Object>> updateMovementCredit(MovementCredit movementCredit) throws ErrorResponseException {
    return movementServiceImpl.updateMovementCreditService(movementCredit)
        .subscribeOn(Schedulers.io())
        .doOnSubscribe(disposable -> log.debug("updateMovementCredit:: init"))
        .andThen(Maybe.just(ResponseEntity.status(HttpStatus.CREATED).build()))
        .doOnSuccess(response -> log.info("updateMovementCredit:: completed"));
  }

  @Override
  public Maybe<ResponseEntity<Object>> deleteMovementCredit(String movementId) throws ErrorResponseException {
    return movementServiceImpl.deleteMovementCredit(movementId)
        .subscribeOn(Schedulers.io())
        .doOnSubscribe(disposable -> log.debug("deleteMovementCredit:: init"))
        .andThen(Maybe.just(ResponseEntity.status(HttpStatus.CREATED).build()))
        .doOnSuccess(response -> log.info("deleteMovementCredit:: completed"));
  }


}
