-- Retrieve the SSNs of employees who work on all projects that John Smith works on
SELECT DISTINCT ssn
  FROM employee
    JOIN works_on ON employee.ssn=works_on.Essn
  WHERE pno IN
    (
      SELECT DISTINCT pno
      FROM employee
        JOIN works_on ON employee.ssn=works_on.Essn
      WHERE Fname='John' and Lname='Smith'
    )
  GROUP BY ssn
  HAVING COUNT(*) = 2;
