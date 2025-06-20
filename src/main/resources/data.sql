INSERT INTO student (student_name, email, institution, password, profile_url) VALUES
('Aina Khalid', 'aina.k@upm.edu.my', 'Universiti Putra Malaysia', '$2a$10$xdvi1q0X5mTten4dNOoS9OyM.39LTvMn6vZAuO7dIgRRVEGk5pF4y', '/images/pfp1.png'),
('Daniel Wong', 'danielw@um.edu.my', 'Universiti Malaya', '$2a$10$0XrENLuhcOFtzdLeswLBLekkYjVGxkok9YqgwjiwpjlbzL5EXGS3G', NULL),
('Nurul Syafiqah', 'syafiqah@upm.edu.my', 'Universiti Putra Malaysia', '$2a$10$qgvR/vHVzrj2a7nAM8kB5.4UCzZhIqE5mS7JrvGSH7rc2O3mMVUXW', NULL),
('Faiz Rahman', 'faiz.r@utm.edu.my', 'Universiti Teknologi Malaysia', '$2a$10$oaeiv1qdCLNBLRw3EYA0uu1eO5kDxvpQEagvz.P3jaXf38d0EKmem', NULL);

INSERT INTO project (title, status, description, category, survey_link, goal_amount, current_amount, end_date, bank_name,
                     account_no, account_holder_name, image_url, student_id) VALUES
      (
          'Smart Hydroponics System', 'Active',
          'IoT-based system for vertical farming','Agriculture', 'http://bit.ly/survey001',
          7000.00, 1200.00, '2025-07-15', 'CIMB',
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
          7000.00, 1500.00, '2025-08-20', 'OCBC',
          '4466-23-466', 'Nurul Syafiqah',
          '/images/p3img.png',4
      ),
      (
          'Smart Rainwater Harvesting System', 'Active',
          'Automated rainwater collection for sustainable use', 'Environment', 'http://bit.ly/survey004',
          10000.00, 1500.00, '2025-09-30', 'RHB',
          '5566-77-889', 'Faiz Rahman',
          '/images/p4img.jpg',4
      ),
      (
          'Compost Recycling Program', 'Active',
          'Community composting initiative to reduce waste', 'Agriculture', 'http://bit.ly/survey005',
          9000.00, 1400.00, '2025-12-01', 'PUBLIC BANK',
          '1234-56-789', 'Aina Khalid',
          '/images/p5img.jpg', 1
      );

INSERT INTO funder (funder_name, email, profile_url, password) VALUES
('Amelia Tan', 'amelia.tan@gmail.com', NULL, '$2a$12$JGriZzgFwZNEeuIzFcocjug9wb0/G0EJ1nco27FZoCvVLmfpfiiWe'),
('Hafiz Zulkifli', 'hafiz.zul@yahoo.com', '/images/pfp2.png', '$2a$12$JGriZzgFwZNEeuIzFcocjug9wb0/G0EJ1nco27FZoCvVLmfpfiiWe'),
('Chloe Lim', 'chloe.lim@outlook.com', NULL, '$2a$12$JGriZzgFwZNEeuIzFcocjug9wb0/G0EJ1nco27FZoCvVLmfpfiiWe');

INSERT INTO fund (amount, payment_method, transaction_date, funder_id, project_id) VALUES
(500.00, 'Online Banking', '2024-04-10', 1,1),
(700.00, 'e-Wallet', '2024-03-05', 2, 1),
(1500.00, 'Online Banking', '2024-10-12', 3, 3),
(1000.00, 'e-Wallet', '2024-07-13', 1, 2),
(2000.00, 'Online Banking', '2024-09-08', 3, 2),
(1400.00, 'e-Wallet', '2024-07-28', 1, 5);
