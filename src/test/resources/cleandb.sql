delete from role;
delete from time_off_request;
delete from schedule_detail;
delete from schedule;
delete from user;
insert into user values (1, 'System', 'Administrator', 'admin', 'password', '2000/01/01', '2019/01/01', '9999/12/31');
insert into role values (1, 'Administrator', 1, 'admin');
insert into user values (2, 'Tammy', 'Johnson', 'TJohnson', 'password', '1992/10/02', '2017/10/18', '9999/12/31');
insert into role values (2, 'Manager', 2, 'TJohnson');
insert into role values (3, 'Administrator', 2, 'TJohnson');
insert into time_off_request values (1, 1, 'admin', '2019/11/15 08:00:00', '2019/11/15 15:00:00');
insert into schedule values (1, '2019/11/11', '2019/11/17');
insert into schedule_detail values (1, 'open', '04:00 PM', 'open', '04:00 PM','open', '04:00 PM','open', '04:00 PM','open', '04:00 PM','open', '02:00 PM','open', '02:00 PM', 'admin', 1, 1);