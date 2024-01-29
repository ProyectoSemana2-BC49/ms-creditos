package com.nttdatabc.mscreditos.service;

import static com.nttdatabc.mscreditos.utils.CreditValidator.verifyCustomerExists;

import com.nttdatabc.mscreditos.model.BalanceAccounts;
import com.nttdatabc.mscreditos.model.Credit;
import com.nttdatabc.mscreditos.model.CustomerExt;
import com.nttdatabc.mscreditos.model.MovementCredit;
import com.nttdatabc.mscreditos.model.SummaryAccountBalance;
import com.nttdatabc.mscreditos.repository.CreditRepository;
import com.nttdatabc.mscreditos.repository.MovementRepository;
import io.reactivex.rxjava3.core.Single;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Clase Report service.
 */
@Service
public class ReportServiceImpl implements ReportService {
  @Autowired
  private CustomerApiExtImpl customerApiExtImpl;
  @Autowired
  private CreditRepository creditRepository;
  @Autowired
  private MovementRepository movementRepository;
  @Autowired
  private CreditServiceImpl creditServiceImpl;

  @Override
  public Single<BalanceAccounts> getBalanceAverageService(String customerId) {
    return Single.defer(() -> {
      CustomerExt customerFound = verifyCustomerExists(customerId, customerApiExtImpl);
      BalanceAccounts balanceAccounts = new BalanceAccounts();
      balanceAccounts.setCustomerId(customerId);

      LocalDate currentDate = LocalDate.now();
      int daysInMonth = currentDate.lengthOfMonth();
      int year = LocalDate.now().getYear();
      int mounth = LocalDate.now().getMonthValue();
      String dateFilter = String.format("%d-%s", year, String.valueOf(mounth).length() == 1 ? "0" + mounth : mounth);

      List<Credit> findCreditsByCustomer = creditRepository.findByCustomerId(customerId);
      List<SummaryAccountBalance> summaryAccountBalances = new ArrayList<>();

      for (Credit credit : findCreditsByCustomer) {
        List<MovementCredit> movements = movementRepository.findByCreditId(credit.getId());

        List<MovementCredit> movementsInCurrentMonth = movements.stream()
            .filter(movement -> movement.getDayCreated().contains(dateFilter))
            .collect(Collectors.toList());

        double totalBalance = movementsInCurrentMonth.stream()
            .mapToDouble(movement -> movement.getAmount().doubleValue())
            .sum();
        BigDecimal averageDailyBalance = BigDecimal.valueOf(totalBalance / daysInMonth);
        SummaryAccountBalance summaryAccountBalance = new SummaryAccountBalance();
        summaryAccountBalance.setAccountId(credit.getId());
        summaryAccountBalance.setBalanceAvg(averageDailyBalance);

        summaryAccountBalances.add(summaryAccountBalance);
      }
      balanceAccounts.setSummaryAccounts(summaryAccountBalances);
      return Single.just(balanceAccounts);
    });
  }
}
