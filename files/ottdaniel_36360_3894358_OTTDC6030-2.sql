--- CS 260, Spring 2019, Lab Test
--- Name: Daniel Ott

--- Table List:
--- Customer ( _CustID_ , FName, LName, PIN)
--- Account ( _AccNumber_ , AccType, AccBalance, Customer (FK), AccOpenLocation, 
---                         AccOpenDate, AccClosedDate, AccStatus)
--- Transaction ( _TransID_ , AccNumber (FK), TransAmount, TransDateTime, 
---                         TransType, TransLocation)


--- 1. (15 points) List the customer id, both name fields, and the account closed 
---    date for customers whose accounts opened in the Central location but have 
---    been closed on or after January 1st, 2018.
select C.CustID,C.FName,C.LName,A.AccClosedDate from Customer C
join Account A on (C.CustID = A.Customer)
where (A.AccOpenLocation = 'Central'
  and A.AccClosedDate >= to_date('01-JAN-2018','DD-Mon-YYYY'));


--- 2. (16 points) For each account opening location, show how many active, closed,
---    and frozen, and accounts there are.  Display the results with the locations
---    and status values in alphabetical order.
---        EXAMPLE OUTPUT (not real numbers - just shown to illustrate format)
---        Central        Active        10
---        Central        Closed        20
---        Central        Frozen         1
---        <similar for other branches>
select AccOpenLocation,AccStatus,count(AccStatus) from Account
group by AccOpenLocation,AccStatus
order by AccOpenLocation asc;


--- 3. (18 points) List the accounts (by account number, in numeric order) 
---    with an account balance greater than the average account balance 
---    for all accounts of the same account type (checking or savings).
select A.AccNumber from Account A
where A.ACCBALANCE > 
  (select avg(A1.AccBalance) from Account A1
  where A.AccType = A1.Acctype)
order by A.AccNumber asc;

--- 4. (18 points) Display the transaction location and the average transaction 
---    amount for each transaction location where that average transaction amount
---    is less than the average transaction amount for all transactions.  Order the
---    results by average transaction amount, largest to smallest.
--- NOTE: for cleaner display, you can (but do not need to do so) CAST a real 
---    number to display with a limited number of decimal places; 
---    e.g. CAST (AVG(value) AS NUMBER(*,2)) 
select distinct T.TransLocation,
  cast(
    (select avg(T1.TransAmount) from Transaction T1
    where T1.TransLocation = T.Translocation) as Number (*,2))
from Transaction T
where 
  ((select avg(T1.TransAmount) from Transaction T1
  where T1.TransLocation = T.Translocation)
  < (select avg(TransAmount) from Transaction))
order by
  ((select avg(T1.TransAmount) from Transaction T1
  where T1.TransLocation = T.Translocation))
  desc;


--- 5. (16 points) Find each customer id, first name, and last name, plus the 
---    amount for the largest 'w' (withdrawal) transaction related to a 
---    savings account which that customer has opened.  Display the results 
---    in order by customer id.
select C.CustID,C.FName,C.LName,max(T.TransAmount) from Customer C
join Account A on (A.AccType = 'savings' and C.CustID = A.Customer)
join Transaction T on (T.TransType = 'w' and T.AccNumber = A.AccNumber)
group by C.CustID,C.FName,C.LName;

--- 6. (17 points) List the customer id, first name and last name of any 
---    customers who have no active accounts.  This includes A) customers who 
---    have accounts, but they only have accounts that are closed and/or frozen, 
---    and also B) customers who do not have any accounts at all.
select C.CustID,C.FName,C.LName from Customer C
where not exists
  (select * from Account A
  where (A.AccStatus = 'Active' and A.Customer = C.CustID));


--- 7. EXTRA CREDIT / ANSWER OPTIONAL (5 points): For each transaction location, 
---    display the number of accounts opened at that location, even if no 
---    accounts were opened at that location.
--- NOTE: at least one transaction location has no accounts opened at that location
select T.TransLocation,count(A.AccNumber) from Transaction T
left outer join Account A on (T.TransLocation = A.AccOpenLocation)
group by T.TransLocation;

