

 --- CS 260, Spring 2019, Lab Test
--- Name: David Miller

--- Table List:
--- Customer ( _CustID_ , FName, LName, PIN)
--- Account ( _AccNumber_ , AccType, AccBalance, Customer (FK), AccOpenLocation, 
---                         AccOpenDate, AccClosedDate, AccStatus)
--- Transaction ( _TransID_ , AccNumber (FK), TransAmount, TransDateTime, 
---                         TransType, TransLocation)


--- 1. (15 points) List the customer id, both name fields, and the account closed 
---    date for customers whose accounts opened in the Central location but have 
---    been closed on or after January 1st, 2018.

SELECT c.CUSTID, c.FNAME, c.LNAME, a.ACCCLOSEDDATE
FROM ACCOUNT a
INNER JOIN CUSTOMER c
ON a.CUSTOMER = c.CUSTID
WHERE a.ACCOPENLOCATION = 'Central'
AND a.ACCCLOSEDDATE >= '01-JAN-18';

--- 2. (16 points) For each account opening location, show how many active, closed,
---    and frozen, and accounts there are.  Display the results with the locations
---    and status values in alphabetical order.
---        EXAMPLE OUTPUT (not real numbers - just shown to illustrate format)
---        Central        Active        10
---        Central        Closed        20
---        Central        Frozen         1
---        <similar for other branches>

SELECT ACCOPENLOCATION, ACCSTATUS, COUNT(*)
FROM ACCOUNT
GROUP BY ACCOPENLOCATION, ACCSTATUS
ORDER BY ACCOPENLOCATION, ACCSTATUS;

--- 3. (18 points) List the accounts (by account number, in numeric order) 
---    with an account balance greater than the average account balance 
---    for all accounts of the same account type (checking or savings).

SELECT a.ACCNUMBER
FROM ACCOUNT a
WHERE a.ACCBALANCE >(
  SELECT AVG(b.ACCBALANCE)
  FROM ACCOUNT b
  WHERE a.ACCTYPE = b.ACCTYPE)
ORDER BY a.ACCNUMBER;

--- 4. (18 points) Display the transaction location and the average transaction 
---    amount for each transaction location where that average transaction amount
---    is less than the average transaction amount for all transactions.  Order the
---    results by average transaction amount, largest to smallest.
--- NOTE: for cleaner display, you can (but do not need to do so) CAST a real 
---    number to display with a limited number of decimal places; 
---    e.g. CAST (AVG(value) AS NUMBER(*,2)) 

SELECT a.TRANSLOCATION, CAST(AVG(a.TRANSAMOUNT) AS NUMBER(*,2))
FROM TRANSACTION a
HAVING AVG(a.TRANSAMOUNT) <(
  SELECT AVG(b.TRANSAMOUNT)
  FROM TRANSACTION b)
GROUP BY a.TRANSLOCATION
ORDER BY AVG(a.TRANSAMOUNT) DESC;

--- 5. (16 points) Find each customer id, first name, and last name, plus the 
---    amount for the largest 'w' (withdrawal) transaction related to a 
---    savings account which that customer has opened.  Display the results 
---    in order by customer id.

SELECT c.CUSTID, c.FNAME, c.LNAME, MAX(t.TRANSAMOUNT)
FROM CUSTOMER c
INNER JOIN ACCOUNT a
ON c.CUSTID = a.CUSTOMER
INNER JOIN TRANSACTION t
ON t.ACCNUMBER = a.ACCNUMBER
WHERE a.ACCTYPE = 'savings'
AND t.TRANSTYPE = 'w'
GROUP BY c.CUSTID, c.FNAME, c.LNAME
ORDER BY c.CUSTID;

--- 6. (17 points) List the customer id, first name and last name of any 
---    customers who have no active accounts.  This includes A) customers who 
---    have accounts, but they only have accounts that are closed and/or frozen, 
---    and also B) customers who do not have any accounts at all.

SELECT a.CUSTID, a.FNAME, a.LNAME
FROM CUSTOMER a
  MINUS
SELECT b.CUSTID, b.FNAME, b.LNAME
FROM CUSTOMER b
INNER JOIN ACCOUNT ac
ON ac.CUSTOMER = b.CUSTID
WHERE ac.ACCSTATUS = 'Active';

--- 7. EXTRA CREDIT / ANSWER OPTIONAL (5 points): For each transaction location, 
---    display the number of accounts opened at that location, even if no 
---    accounts were opened at that location.
--- NOTE: at least one transaction location has no accounts opened at that location

SELECT t.TRANSLOCATION, COUNT(a.ACCOPENLOCATION)
FROM TRANSACTION t
LEFT JOIN ACCOUNT a
ON t.TRANSLOCATION = a.ACCOPENLOCATION
GROUP BY t.TRANSLOCATION
ORDER BY t.TRANSLOCATION;

