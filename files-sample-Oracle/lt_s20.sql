

-- -- CS 260, Fall 2019, Lab Test
-- -- Name: Student 20

-- -- Table List:
-- -- Customer ( _CustID_ , FName, LName, PIN)
-- -- Account ( _AccNumber_ , AccType, AccBalance, Customer (FK), AccOpenLocation, 
-- --                         AccOpenDate, AccClosedDate, AccStatus)
-- -- Transaction ( _TransID_ , AccNumber (FK), TransAmount, TransDateTime, 
-- --                         TransType, TransLocation)


-- -- 1. (15 points) List the customer id, both name fields, and the account closed 
-- --    date for customers whose accounts opened in the Central location but have 
-- --    been closed on or after March 1st, 2017.
SELECT CustId, FName, LName, AccClosedDate
FROM Customer c
JOIN Account a ON a.Customer = c.CustId
WHERE AccOpenLocation = 'Central' AND AccClosedDate >= '01-MAR-2017';


-- -- 2. (16 points) For each account opening location, show how many active, closed,
-- --    and frozen, and accounts there are.  Display the results with the locations
-- --    and account status values in alphabetical order.
-- --        EXAMPLE OUTPUT (not real numbers - just shown to illustrate format)
-- --        Central        Active        10
-- --        Central        Closed        20
-- --        Central        Frozen         1
-- --        <similar for other branches>
SELECT AccOpenLocation, AccStatus, COUNT(AccNumber)
FROM Account
GROUP BY AccOpenLocation, AccStatus
ORDER BY AccOpenLocation, AccStatus;


-- -- 3. (18 points) List the accounts (by account number, in numeric order) 
-- --    with an account balance greater than the average account balance 
-- --    for all accounts of the same account type (checking or savings).
SELECT AccNumber
FROM Account a1
WHERE AccBalance > (
    SELECT AVG(AccBalance) 
    FROM Account a2
    WHERE a1.AccType = a2.AccType
    )
ORDER BY AccNumber;

-- -- 4. (18 points) Display the transaction location and the average transaction 
-- --    amount for each transaction location where that average transaction amount
-- --    is less than the average transaction amount for all transactions.  Order the
-- --    results by average transaction amount, largest to smallest.
-- -- NOTE: for cleaner display, you can (but do not need to do so) CAST a real 
-- --    number to display with a limited number of decimal places; 
-- --    e.g. CAST (AVG(value) AS NUMBER(*,2)) 
SELECT TransLocation, AVG(TransAmount)
FROM Transaction t1
GROUP BY TransLocation
HAVING AVG(TransAmount) < (
    SELECT AVG(TransAmount)
    FROM Transaction t2
    )
ORDER BY AVG(TransAmount)
DESC;


-- -- 5. (16 points) Find each customer id, first name, and last name, plus the 
-- --    amount for the largest 'w' (withdrawal) transaction related to a 
-- --    savings account which that customer has opened.  Display the results 
-- --    in order by customer id.
SELECT CustId, FName, LName, MAX(TransAmount)
FROM Customer c
JOIN Account a ON a.Customer = c.CustId
JOIN Transaction t ON t.AccNumber = a.AccNumber
GROUP BY CustId,FName,LName,TransType,AccType
HAVING TransType = 'w' AND AccType = 'savings'
ORDER BY CustId;


-- -- 6. (17 points) List the customer id, first name and last name of any 
-- --    customers who have no active accounts.  This includes A) customers who 
-- --    have accounts, but they only have accounts that are closed and/or frozen, 
-- --    and also B) customers who do not have any accounts at all.
SELECT CustId, FName, LName
FROM Customer c
MINUS
SELECT CustId,FName, LName
FROM Customer
WHERE CustId IN 
(
    SELECT Customer
    FROM Account
    WHERE AccStatus = 'Active'
    );


-- -- 7. EXTRA CREDIT / ANSWER OPTIONAL (5 points): For each transaction location, 
-- --    display the number of accounts opened at that location, even if no 
-- --    accounts were opened at that location.
-- -- NOTE: at least one transaction location has no accounts opened at that location
SELECT TransLocation, COUNT(a.AccNumber)
FROM Transaction t
LEFT JOIN Account a ON a.AccOpenLocation = t.TransLocation
GROUP BY TransLocation
ORDER BY TransLocation;

