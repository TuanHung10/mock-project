
-- =============================================
-- INSERT DATA 
-- =============================================
-- Add data Department
INSERT INTO Department(	`name`, 		total_member, 	`type`, 		created_date) 
VALUES
						(N'Marketing'	, 		1,		'Dev', 			'2022-03-05'),
						(N'Sale'		, 		2,		'Test', 		'2021-08-06'),
						(N'Customer service'		, 		3,		'ScrumMaster', 	'2022-03-07'),
						(N'HR'		, 		4,		'PM', 			'2022-12-01'),
						(N'Accounting'	, 		5,		'Dev', 			'2022-09-10'),
						(N'Finance'	, 		6,		'ScrumMaster', 	'2021-03-10'		),
						(N'Research & Development', 		7,		'PM', 			'2022-03-10'		),
						(N'Administration'	, 		3,		'Test', 		'2022-08-07'),
						(N'Production'		, 		4,		'PM', 			'2022-04-05'),
						(N'IT'	, 		3,		'Dev', 			'2021-04-09');
                    
-- Add data Account
-- Password: 123456
INSERT INTO `Account`(	username		,						`password`																,	first_name			,	last_name	,		`role`		,	department_id	)
VALUES 					(	'tuanhung10'	,   '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'		,	'Hung'				,	'Nguyen'		,		'ADMIN'		,		'5'		),
						   (	'phucto'		,	'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'				,	'Phuc'				,	'To'			,		'MANAGER'	,		'1'		),
                     (	'hkn30'		,	'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'				,	'Nam'					,	'Hoang'		,		'ADMIN'		,		'2'		),
                     (	'pdhm31'		,	'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'				,	'Minh'				,	'Pham'		,		'EMPLOYEE'	,		'1'		),
                     (	'tuanduong21'	,   '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'		,	'Duong'				,	'Pham'		,		'ADMIN'		,		'2'		),
                     (	'doanhminh'		,   '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'		,	'Minh'				,	'Do'			,		'EMPLOYEE'	,		'2'		),
                     (	'quangkhai11'	,   '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'		,	'Khai'				,	'Nguyen'		,		'ADMIN'		,		'7'		),
                     (	'khaidung19'		,   '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'	,	'Dung'				,	'Doan'		,		'MANAGER'	,		'8'		),
                     (	'thinhhoang'		,   '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'	,	'Thinh'				,	'Hoang'		,		'ADMIN'		,		'9'		),
                     (	'tiendat09'	,   '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'			,	'Dat'					,	'Nguyen'		,		'MANAGER'	,		'10'		),
                     (	'ngockhang'	,   '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'			,	'Khang'				,	'Nguyen'		,		'EMPLOYEE'	,		'2'		),
                     (	'hoangduong69'	,   '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'		,	'Duong'				,	'Luyen'		,		'MANAGER'	,		'3'		),
                     (	'namvu07'	,   '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'			,	'Nam'					,	'Vu'			,		'ADMIN'		,		'4'		),
                     (	'hoangnam'	,   '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'			,	'Nam'				,	'Tran'			,		'EMPLOYEE'	,		'6'		);