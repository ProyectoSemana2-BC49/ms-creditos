package com.nttdatabc.mscreditos.controller;


import static com.nttdatabc.mscreditos.utils.Constantes.PREFIX_PATH;

import com.nttdatabc.mscreditos.model.BalanceAccounts;
import com.nttdatabc.mscreditos.service.ReportServiceImpl;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Report controller.
 */
@RestController
@RequestMapping(PREFIX_PATH)
@Slf4j
public class ReportController implements ReportControllerApi {

  @Autowired
  private ReportServiceImpl reportServiceImpl;

  @Override
  public Single<ResponseEntity<BalanceAccounts>> getBalanceCredit(String customerId) {
    return reportServiceImpl.getBalanceAverageService(customerId)
        .subscribeOn(Schedulers.io())
        .doOnSubscribe(disposable -> log.debug("getBalanceCredit:: init"))
        .map(ResponseEntity::ok)
        .doOnSuccess(cus -> log.debug("getBalanceCredit:: completed"));
  }
}
