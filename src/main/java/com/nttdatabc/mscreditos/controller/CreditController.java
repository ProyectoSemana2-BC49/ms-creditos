package com.nttdatabc.mscreditos.controller;

import static com.nttdatabc.mscreditos.utils.Constantes.PREFIX_PATH;

import com.nttdatabc.mscreditos.model.Credit;
import com.nttdatabc.mscreditos.service.CreditServiceImpl;
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
 * Controller Credit.
 */
@RestController
@RequestMapping(PREFIX_PATH)
@Slf4j
public class CreditController implements CreditControllerApi {

  @Autowired
  private CreditServiceImpl creditServiceImpl;

  @Override
  public Observable<ResponseEntity<List<Credit>>> getAllCredits() {
    return creditServiceImpl.getAllCreditsService()
        .subscribeOn(Schedulers.io())
        .doOnSubscribe(disposable -> log.debug("getAllCredits:: init"))
        .doOnComplete(() -> log.info("getAllCredits: completed"))
        .map(ResponseEntity::ok);
  }

  @Override
  public Single<ResponseEntity<Credit>> getCreditById(String creditId) throws ErrorResponseException {
    return creditServiceImpl.getCreditByIdService(creditId)
        .subscribeOn(Schedulers.io())
        .doOnSubscribe(disposable -> log.debug("getCreditById:: init"))
        .map(ResponseEntity::ok)
        .doOnSuccess(cus -> log.debug("getCreditById:: completed"));
  }

  @Override
  public Maybe<ResponseEntity<Object>> createCredit(Credit credit) throws ErrorResponseException {
    return creditServiceImpl.createCreditService(credit)
        .subscribeOn(Schedulers.io())
        .doOnSubscribe(disposable -> log.debug("createCredit:: init"))
        .andThen(Maybe.just(ResponseEntity.status(HttpStatus.CREATED).build()))
        .doOnSuccess(response -> log.info("createCredit:: completed"));
  }

  @Override
  public Maybe<ResponseEntity<Object>> updateCredit(Credit credit) throws ErrorResponseException {
    return creditServiceImpl.updateCreditService(credit)
        .subscribeOn(Schedulers.io())
        .doOnSubscribe(disposable -> log.debug("updateCredit:: init"))
        .andThen(Maybe.just(ResponseEntity.status(HttpStatus.OK).build()))
        .doOnSuccess(response -> log.info("updateCredit:: completed"));
  }

  @Override
  public Maybe<ResponseEntity<Object>> deleteCreditById(String creditId) throws ErrorResponseException {
    return creditServiceImpl.deleteCreditById(creditId)
        .subscribeOn(Schedulers.io())
        .doOnSubscribe(disposable -> log.debug("deleteCreditById:: init"))
        .andThen(Maybe.just(ResponseEntity.status(HttpStatus.OK).build()))
        .doOnSuccess(response -> log.info("deleteCreditById:: completed"));
  }

  @Override
  public Observable<ResponseEntity<List<Credit>>> getCreditsByCustomerId(String customerId) throws ErrorResponseException {
    return creditServiceImpl.getCreditsByCustomerId(customerId)
        .subscribeOn(Schedulers.io())
        .doOnSubscribe(disposable -> log.debug("getCreditsByCustomerId:: init"))
        .doOnComplete(() -> log.info("getCreditsByCustomerId: completed"))
        .map(ResponseEntity::ok);
  }
}
