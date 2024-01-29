package com.nttdatabc.mscreditos.service;

import static com.nttdatabc.mscreditos.utils.Constantes.EX_ERROR_CONFLICTO_CUSTOMER_PERSONA;
import static com.nttdatabc.mscreditos.utils.Constantes.EX_NOT_FOUND_RECURSO;
import static com.nttdatabc.mscreditos.utils.Constantes.INTEREST_RATE;
import static com.nttdatabc.mscreditos.utils.Constantes.MAX_SIZE_ACCOUNT_CUSTOMER_PERSONA;
import static com.nttdatabc.mscreditos.utils.CreditValidator.validateCreditsEmpty;
import static com.nttdatabc.mscreditos.utils.CreditValidator.validateCreditsNoNulls;
import static com.nttdatabc.mscreditos.utils.CreditValidator.verifyCustomerExists;
import static com.nttdatabc.mscreditos.utils.CreditValidator.verifyTypeCredits;
import static com.nttdatabc.mscreditos.utils.CreditValidator.verifyValues;

import com.nttdatabc.mscreditos.model.Credit;
import com.nttdatabc.mscreditos.model.CustomerExt;
import com.nttdatabc.mscreditos.model.TypeCustomer;
import com.nttdatabc.mscreditos.repository.CreditRepository;
import com.nttdatabc.mscreditos.utils.Utilitarios;
import com.nttdatabc.mscreditos.utils.exceptions.errors.ErrorResponseException;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Credit service.
 */
@Service
public class CreditServiceImpl implements CreditService {

  @Autowired
  private CreditRepository creditRepository;
  @Autowired
  private CustomerApiExtImpl customerApiExtImpl;

  @Override
  public Observable<List<Credit>> getAllCreditsService() {
    return Observable.defer(() -> Observable.just(creditRepository.findAll()));
  }

  @Override
  public Single<Credit> getCreditByIdService(String creditId) throws ErrorResponseException {
    return Single.defer(() -> {
      Optional<Credit> credit = creditRepository.findById(creditId);
      return Single.just(credit.orElseThrow(() -> new ErrorResponseException(EX_NOT_FOUND_RECURSO,
          HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND)));
    });
  }

  @Override
  public Completable createCreditService(Credit credit) throws ErrorResponseException {
    return Completable.fromAction(() -> {
      validateCreditsNoNulls(credit);
      validateCreditsEmpty(credit);
      verifyTypeCredits(credit);
      verifyValues(credit);
      CustomerExt customerFound = verifyCustomerExists(credit.getCustomerId(), customerApiExtImpl);
      List<Credit> listCreditsByCustomer = getCreditsByCustomerId(credit.getCustomerId()).blockingSingle();

      if (customerFound.getType().equalsIgnoreCase(TypeCustomer.PERSONA.toString())) {
        if (listCreditsByCustomer.size() >= MAX_SIZE_ACCOUNT_CUSTOMER_PERSONA) {
          throw new ErrorResponseException(EX_ERROR_CONFLICTO_CUSTOMER_PERSONA,
              HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT);
        }
      }

      credit.setId(Utilitarios.generateUuid());
      credit.setDateOpen(LocalDateTime.now().toString());
      credit.setInterestRate(BigDecimal.valueOf(INTEREST_RATE));
      creditRepository.save(credit);
    });
  }

  @Override
  public Completable updateCreditService(Credit credit) throws ErrorResponseException {
    return Completable.fromAction(() -> {
      validateCreditsNoNulls(credit);
      validateCreditsEmpty(credit);
      verifyTypeCredits(credit);
      Optional<Credit> getCreditById = creditRepository.findById(credit.getId());
      if (getCreditById.isEmpty()) {
        throw new ErrorResponseException(EX_NOT_FOUND_RECURSO,
            HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND);
      }
      Credit creditFound = getCreditById.get();
      creditFound.setMountLimit(credit.getMountLimit());
      creditFound.setTypeCredit(credit.getTypeCredit());
      creditRepository.save(creditFound);
    });
  }

  @Override
  public Completable deleteCreditById(String creditId) throws ErrorResponseException {
    return Completable.fromAction(() -> {
      Optional<Credit> creditFindByIdOptional = creditRepository.findById(creditId);
      if (creditFindByIdOptional.isEmpty()) {
        throw new ErrorResponseException(EX_NOT_FOUND_RECURSO,
            HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND);
      }
      creditRepository.delete(creditFindByIdOptional.get());
    });
  }

  @Override
  public Observable<List<Credit>> getCreditsByCustomerId(String customerId) throws ErrorResponseException {
    return Observable.defer(() -> {
      verifyCustomerExists(customerId, customerApiExtImpl);
      return Observable.just(creditRepository.findByCustomerId(customerId));
    });
  }


}
