INSERT INTO student (student_name, email, institution, password, profile_url)
VALUES
    ('Aina Khalid', 'aina.k@upm.edu.my', 'Universiti Putra Malaysia', 'pass1234', NULL),
    ('Daniel Wong', 'danielw@um.edu.my', 'Universiti Malaya', 'dw2024', NULL),
    ('Nurul Syafiqah', 'syafiqah@upm.edu.my', 'Universiti Putra Malaysia', 'flower88', NULL),
    ('Faiz Rahman', 'faiz.r@utm.edu.my', 'Universiti Teknologi Malaysia', 'faizpass', NULL);

INSERT INTO project (
    title,
    status,
    description,
    category,
    survey_link,
    goal_amount,
    current_amount,
    end_date,
    bank_name,
    account_no,
    account_holder_name,
    image_url,
    student_id
) VALUES
      (
          'Smart Hydroponics System', 'Active',
          'IoT-based system for vertical farming','Agriculture', 'http://bit.ly/survey001',
          5000.00, 1200.00, '2025-07-15', 'CIMB',
          '1234-56-789', 'Aina Khalid',
          '/images/p1img.jpg',1
      ),

      (
          'Organic Fertiliser Campaign', 'Completed',
          'Promoting eco-friendly fertilisers', 'Technology','http://bit.ly/survey002',
          3000.00, 3000.00, '2025-04-10', 'MAYBANK',
          '0987-45-123', 'Wong Kai Qi',
          '/images/p2img.jpg',2
      ),

      (
          'Urban Farming Education App', 'Active',
          'Educational app for urban agriculture', 'Environment','http://bit.ly/survey003',
          7000.00, 4200.00, '2025-08-20', 'OCBC',
          '4466-23-466', 'Nurul Syafiqah',
          '/images/p3img.png',3
      );

INSERT INTO funder (funder_name, email, profile_url, password)
VALUES ('Amelia Tan', 'amelia.tan@gmail.com', NULL, 'pass123'),
       ('Hafiz Zulkifli', 'hafiz.zul@yahoo.com', NULL, 'pass123'),
       ('Chloe Lim', 'chloe.lim@outlook.com', NULL, 'pass123');


INSERT INTO fund (amount, payment_method, transaction_date) VALUES
(500.00, 'Online Banking', '2024-04-10'),
(700.00, 'e-Wallet', '2024-03-05'),
(1500.00, 'Online Banking', '2024-10-12'),
(1000.00, 'e-Wallet', '2024-07-13');

UPDATE project
SET student_id = 1
WHERE project_id = 1;

UPDATE project
SET student_id = 2
WHERE project_id = 2;

UPDATE project
SET student_id = 3
WHERE project_id = 3;

UPDATE fund
SET funder_id = 1, project_id = 1
WHERE fund_id = 1;

UPDATE fund
SET funder_id = 2, project_id = 1
WHERE fund_id = 2;

UPDATE fund
SET funder_id = 3, project_id = 3
WHERE fund_id = 3;

UPDATE fund
SET funder_id = 1, project_id = 2
WHERE fund_id = 4;









