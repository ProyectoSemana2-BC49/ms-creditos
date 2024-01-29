package com.nttdatabc.mscreditos.service;

import com.nttdatabc.mscreditos.model.Credit;
import com.nttdatabc.mscreditos.utils.exceptions.errors.ErrorResponseException;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import java.util.List;

/**
 * Reposirotory credit.
 */
public interface CreditService {
  Observable<List<Credit>> getAllCreditsService();

  Single<Credit> getCreditByIdService(String creditId) throws ErrorResponseException;

  Completable createCreditService(Credit credit) throws ErrorResponseException;

  Completable updateCreditService(Credit credit) throws ErrorResponseException;

  Completable deleteCreditById(String creditId) throws ErrorResponseException;

  Observable<List<Credit>> getCreditsByCustomerId(String customerId) throws ErrorResponseException;
}
