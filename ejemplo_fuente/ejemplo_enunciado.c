select * from employees where employee_id = 1;
select ciudades from pais where pais.nombre <> "venezuela";
select * from employees JOIN departamento ON employee.departamento=departamento.id where employee_id = 1;