package com.nttdatabc.mscreditos.service;

import static com.nttdatabc.mscreditos.utils.Constantes.EX_ERROR_AMOUNT_CREDIT;
import static com.nttdatabc.mscreditos.utils.Constantes.EX_ERROR_INSTALLMENTS;
import static com.nttdatabc.mscreditos.utils.Constantes.EX_ERROR_PAYMENT_LIMIT;
import static com.nttdatabc.mscreditos.utils.Constantes.EX_ERROR_REQUEST;
import static com.nttdatabc.mscreditos.utils.Constantes.EX_NOT_FOUND_RECURSO;
import static com.nttdatabc.mscreditos.utils.Constantes.MAX_SIZE_INSTALLMENTS;
import static com.nttdatabc.mscreditos.utils.MovementValidator.paymentIsValid;
import static com.nttdatabc.mscreditos.utils.MovementValidator.validateCreditRegister;
import static com.nttdatabc.mscreditos.utils.MovementValidator.validateMovementEmpty;
import static com.nttdatabc.mscreditos.utils.MovementValidator.validateMovementNoNulls;
import static com.nttdatabc.mscreditos.utils.MovementValidator.verifyValues;

import com.nttdatabc.mscreditos.model.Credit;
import com.nttdatabc.mscreditos.model.MovementCredit;
import com.nttdatabc.mscreditos.model.PaidInstallment;
import com.nttdatabc.mscreditos.model.StatusCredit;
import com.nttdatabc.mscreditos.repository.MovementRepository;
import com.nttdatabc.mscreditos.utils.Utilitarios;
import com.nttdatabc.mscreditos.utils.exceptions.errors.ErrorResponseException;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Clase service movements.
 */
@Service
public class MovementServiceImpl implements MovementService {

  @Autowired
  private MovementRepository movementRepository;
  @Autowired
  private CreditServiceImpl creditServiceImpl;

  @Override
  public Completable createMovementCreditService(MovementCredit movementCredit) throws ErrorResponseException {
    return Completable.fromAction(() -> {
      validateMovementNoNulls(movementCredit);
      validateMovementEmpty(movementCredit);
      validateMovementEmpty(movementCredit);
      validateCreditRegister(movementCredit.getCreditId(), creditServiceImpl);
      verifyValues(movementCredit);

      Credit infoCreditById = creditServiceImpl.getCreditByIdService(movementCredit.getCreditId()).blockingGet();
      if (infoCreditById.getMountLimit().doubleValue() < movementCredit.getAmount().doubleValue()) {
        throw new ErrorResponseException(EX_ERROR_AMOUNT_CREDIT,
            HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT);
      }
      if (movementCredit.getTotalInstallments() > MAX_SIZE_INSTALLMENTS) {
        throw new ErrorResponseException(EX_ERROR_INSTALLMENTS,
            HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT);
      }

      movementCredit.setId(Utilitarios.generateUuid());
      movementCredit.setDayCreated(LocalDateTime.now().toString());
      movementCredit.setStatus(StatusCredit.ACTIVO.toString());
      movementCredit.setPaidInstallments(new ArrayList<PaidInstallment>());
      movementRepository.save(movementCredit);

      // actualizar crédito
      infoCreditById.setMountLimit(infoCreditById.getMountLimit().subtract(movementCredit.getAmount()));
      creditServiceImpl.updateCreditService(infoCreditById).blockingAwait();
    });
  }

  @Override
  public Observable<List<MovementCredit>> getMovementsCreditsByCreditIdService(String creditId) throws ErrorResponseException {
    return Observable.defer(() -> {
      validateCreditRegister(creditId, creditServiceImpl);
      return Observable.just(movementRepository.findByCreditId(creditId));
    });
  }

  @Override
  public Single<MovementCredit> getMovementCreditByIdService(String movementId) throws ErrorResponseException {
    return Single.defer(() -> {
      Optional<MovementCredit> movement = movementRepository.findById(movementId);
      return Single.just(movement.orElseThrow(() -> new ErrorResponseException(EX_NOT_FOUND_RECURSO,
          HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND)));
    });
  }

  @Override
  public Completable createPaymentInstallmentByMovementId(String movementId, PaidInstallment paidInstallment) throws ErrorResponseException {
    return Completable.fromAction(() -> {
      if (!paymentIsValid(paidInstallment)) {
        throw new ErrorResponseException(EX_ERROR_REQUEST, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
      }

      MovementCredit movementCredit = getMovementCreditByIdService(movementId).blockingGet();
      if (movementCredit.getTotalInstallments().intValue() <= movementCredit.getPaidInstallments().size()) {
        throw new ErrorResponseException(EX_ERROR_PAYMENT_LIMIT,
            HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT);
      }
      validateCreditRegister(movementCredit.getCreditId(), creditServiceImpl);
      paidInstallment.setId(Utilitarios.generateUuid());
      paidInstallment.setDatePayment(LocalDateTime.now().toString());
      paidInstallment.setInstallmentNumber(movementCredit.getPaidInstallments().size() + 1);
      movementCredit.getPaidInstallments().add(paidInstallment);

      updateMovementCreditService(movementCredit).blockingAwait();
      MovementCredit movementCreditVerify = getMovementCreditByIdService(movementId).blockingGet();
      if (movementCreditVerify.getTotalInstallments().intValue() == movementCredit.getPaidInstallments().size()) {
        movementCreditVerify.setStatus(StatusCredit.PAGADO.toString());
        updateMovementCreditService(movementCreditVerify).blockingAwait();
      }
    });
  }

  @Override
  public Completable updateMovementCreditService(MovementCredit movementCredit) throws ErrorResponseException {
    return Completable.fromAction(() -> {
      MovementCredit movementFound = getMovementCreditByIdService(movementCredit.getId()).blockingGet();
      movementFound.setAmount(movementCredit.getAmount());
      movementFound.setStatus(movementCredit.getStatus());
      movementFound.setDueDate(movementCredit.getDueDate());
      movementFound.setPaidInstallments(movementCredit.getPaidInstallments());
      movementFound.setTotalInstallments(movementCredit.getTotalInstallments());
      movementRepository.save(movementFound);
    });
  }

  @Override
  public Completable deleteMovementCredit(String movementId) throws ErrorResponseException {
    return Completable.fromAction(() -> {
      Optional<MovementCredit> movementFindByIdOptional = movementRepository.findById(movementId);
      if (movementFindByIdOptional.isEmpty()) {
        throw new ErrorResponseException(EX_NOT_FOUND_RECURSO, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND);
      }
      movementRepository.delete(movementFindByIdOptional.get());
    });
  }

}
