package org.udg.caes.account;

import mockit.*;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAccountService {

  @Injectable Account acc1;
  @Injectable Account acc2;


  @Test
  void testTransfer(@Tested AccountService as, @Mocked AccountManager am)  {
    as = new AccountService();
    as.setAccountManager(am);
    acc1 = new Account("Miguel",100);
    acc2 = new Account("Angel",350);

    new Expectations(){{
      am.findAccount("Miguel"); result = acc1;
      am.findAccount("Angel"); result = acc2;
    }};

    as.transfer("Miguel","Angel",50);

    new Verifications(){{
      am.updateAccount(acc1); times = 1;
      am.updateAccount(acc2); times = 1;
      acc2.credit(0); times = 1;
      acc1.debit(0); times = 1;
      assertEquals(acc1.getBalance(), 50);
      assertEquals(acc2.getBalance(),400);
    }};
  }
}