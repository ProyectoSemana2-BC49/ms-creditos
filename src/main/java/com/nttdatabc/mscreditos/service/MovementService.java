package com.nttdatabc.mscreditos.service;

import com.nttdatabc.mscreditos.model.MovementCredit;
import com.nttdatabc.mscreditos.model.PaidInstallment;
import com.nttdatabc.mscreditos.utils.exceptions.errors.ErrorResponseException;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import java.util.List;

/**
 * Repository movement.
 */
public interface MovementService {
  Completable createMovementCreditService(MovementCredit movementCredit) throws ErrorResponseException;

  Observable<List<MovementCredit>> getMovementsCreditsByCreditIdService(String creditId) throws ErrorResponseException;

  Single<MovementCredit> getMovementCreditByIdService(String movementId) throws ErrorResponseException;

  Completable createPaymentInstallmentByMovementId(String movementId, PaidInstallment paidInstallment) throws ErrorResponseException;

  Completable updateMovementCreditService(MovementCredit movementCredit) throws ErrorResponseException;

  Completable deleteMovementCredit(String movementId) throws ErrorResponseException;
}
