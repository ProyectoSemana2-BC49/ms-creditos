package com.nttdatabc.mscreditos.service;

import com.nttdatabc.mscreditos.model.BalanceAccounts;
import io.reactivex.rxjava3.core.Single;

/**
 * Report service interface.
 */
public interface ReportService {
  Single<BalanceAccounts> getBalanceAverageService(String customerId);
}
